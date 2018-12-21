package edu.upm.midas.data.relational.service.helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.upm.midas.common.util.Common;
import edu.upm.midas.common.util.TimeProvider;
import edu.upm.midas.common.util.UniqueId;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.email.model.EmailStatus;
import edu.upm.midas.email.service.EmailService;
import edu.upm.midas.model.jpa.*;
import edu.upm.midas.model.user.Response;
import edu.upm.midas.model.user.UserRegistrationForm;
import edu.upm.midas.model.user.UserUpdateForm;
import edu.upm.midas.service.jpa.*;
import edu.upm.midas.service.jpa.impl.LoginService;
import edu.upm.midas.service.jpa.impl.PersonLoginService;
import edu.upm.midas.token.component.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by gerardo on 18/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className PersonHelper
 * @see
 */
@Service
public class PersonHelper {

    private static final Logger logger = LoggerFactory.getLogger(PersonHelper.class);
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private AcademicInfoService academicInfoService;
    @Autowired
    private PersonService personService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailConfirmationService emailConfirmationService;
    @Autowired
    private PersonTokenService personTokenService;
    @Autowired
    private PersonLoginService personLoginService;
    @Autowired
    private LoginService loginService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TimeProvider timeProvider;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Constants constants;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Common common;

    @Value("${spring.datasource.url}")
    private String spring_datasource_url;
    @Value("${spring.datasource.username}")
    private String spring_datasource_username;
    @Value("${spring.datasource.password}")
    private String spring_datasource_password;

    @PostConstruct
    public void setup(){
        System.out.println(
                "MySQL Variables: URL: " + this.spring_datasource_url + "\n" +
                "USERNAME: " + this.spring_datasource_username + "\n" +
                "PASS: " + this.spring_datasource_password);
    }

    /**
     * @param email
     * @return
     */
    public Person findByEmailAndStatusOK(String email){
        Object[] person = personService.findByIdAndStatusNative(email, Constants.OK_STATUS);
        return createPerson(person);
    }


    /**
     * @param email
     * @return
     */
    public Person findByEmailAndStatusNW(String email){
        Object[] person = personService.findByIdAndStatusNative(email, Constants.NEW_STATUS);
        return createPerson(person);
    }


    /**
     * @param person
     * @return
     */
    public Person createPerson(Object[] person){
        Person user = null;

        if (person!=null) {
            user = new Person();
            user.setPersonId((String) person[0]);
            user.setStatus( getStatus( (String) person[1] ) );
            user.setEnabled( (boolean) person[2] );
            user.setFirstName((String) person[3]);
            user.setLastName((String) person[4]);
            user.setPassword((String) person[5]);
            user.setAcademicInfoId((int) person[10]);
            user.setProfileId((String) person[6]);
            user.setDate((Date) person[16]);
            user.setDatetime((Timestamp) person[17]);
            user.setLastUpdate((Timestamp) person[18]);

            AcademicInfo academicInfo = new AcademicInfo();
            academicInfo.setAcademicInfoId((int) person[10]);
            academicInfo.setInstitutionName((String) person[11]);
            academicInfo.setCountryId((int) person[12]);
            academicInfo.setOccupation((String) person[13]);
            academicInfo.setInterest((String) person[14]);

            user.setAcademicInfoByAcademicInfoId(academicInfo);

            Profile profile = new Profile();
            profile.setProfileId((String) person[6]);
            profile.setName((String) person[7]);
            profile.setAuthority( getAuthority( (String) person[8] ) );
            profile.setEnabled((boolean) person[9]);

            user.setProfileByProfileId(profile);

        }
        return user;
    }

    /**
     * @param status
     * @return
     */
    public Status getStatus(String status){
        Status statusFound = Status.DW;
        for (Status stat: Status.values()) {
            /*System.out.println(stat.toString().trim() + ", " + status.trim() + " = " + (stat.toString().trim().equals(status.trim())));*/
            if (stat.toString().trim().equals(status.trim())) statusFound = stat;
        }
        return statusFound;
    }


    /**
     * @param authority
     * @return
     */
    public AuthorityName getAuthority(String authority){
        AuthorityName authorityFound = AuthorityName.ROLE_USER;
        for (AuthorityName authorityName: AuthorityName.values()) {
            if (authorityName.equals(authority.trim())) authorityFound = authorityName;
        }
        return authorityFound;
    }

    /**
     * @param user
     * @param device
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public boolean saveNewUser(UserRegistrationForm user, Device device) throws JsonProcessingException {
        AcademicInfo academicInfo = new AcademicInfo();
        academicInfo.setInstitutionName( user.getInstitution() );
        academicInfo.setCountryId( user.getCountry() );
        academicInfo.setOccupation( user.getOccupation() );
        academicInfo.setInterest( user.getInterest() );

//        logger.info( "Object Persist: {}",objectMapper.writeValueAsString(academicInfo) );
        academicInfoService.save(academicInfo);
//        logger.info( "Object Persist: {}",objectMapper.writeValueAsString(academicInfo) );

        Person person = new Person();
        person.setPersonId( user.getEmail() );
        person.setStatus( Status.NW );
        person.setEnabled( false );
        person.setFirstName( user.getFirstName() );
        person.setLastName( user.getLastName() );
        person.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        person.setProfileId(Constants.USER_ROLE);
        person.setAcademicInfoId( academicInfo.getAcademicInfoId() );
        person.setDate(timeProvider.getNow());
        person.setDatetime(timeProvider.getTimestamp());
        person.setLastUpdate(timeProvider.getTimestamp());

//        logger.info( "Object Persist: {}",objectMapper.writeValueAsString(person) );
        personService.save(person);
//        logger.info( "Object Persist: {}",objectMapper.writeValueAsString(person) );

        boolean isSuccessful =  ( personService.findById(person.getPersonId()) != null );// true;//(findByEmailAndStatusNW( user.getEmail() ) != null );

        //Enviar correo de confirmación
        if (isSuccessful){

            //<editor-fold desc="GENERACIÓN DE TOKEN">
            String token = jwtTokenUtil.generateToken(person, device);
            System.out.println(token);

            Token oToken = new Token();
            oToken.setToken(token);
            oToken.setType("C");//De Confirmación
            oToken.setEnabled(true);
            oToken.setExpiration(0);
            oToken.setScope("");
            oToken.setDate(timeProvider.getNow());
            oToken.setDatetime(timeProvider.getTimestamp());
            oToken.setLastUpdate(timeProvider.getTimestamp());
//            logger.info( "Object Persist: {}",objectMapper.writeValueAsString(oToken) );
            tokenService.save(oToken);
//            logger.info( "Object Persist: {}",objectMapper.writeValueAsString(oToken) );
            //</editor-fold>

            String tokenConfirmationLink =  constants.HTTP_HEADER +
                                            /*Constants.TWO_POINTS + Constants.TWO_SLASH +*/
                                            constants.URL_DISNET_WEB_APP +
                                            constants.USER_CONFIRMATION_PATH +
                                            Constants.PARAM_SEP_2 +
                                            constants.PARAMETER_CONFIRMATION_TOKEN_NAME +
                                            Constants.EQUAL + token;
//            System.out.println("GLG"+tokenConfirmationLink);
            Context context = new Context();
            context.setVariable("user", person.getFirstName() + " " + person.getLastName());
            context.setVariable("email", person.getPersonId());
            context.setVariable("token", tokenConfirmationLink);
            EmailStatus confirmationEmailStatus= emailService.sendConfirmation( person.getPersonId(), context );

            EmailConfirmation emailConfirmation = new EmailConfirmation();
            emailConfirmation.setPersonId( person.getPersonId() );
            emailConfirmation.setToken( oToken.getToken() );
            emailConfirmation.setSent(confirmationEmailStatus.isSuccess());
            emailConfirmation.setSentDate(timeProvider.getNow());
            emailConfirmation.setSentDatetime(timeProvider.getTimestamp());
            emailConfirmation.setEnabled(false);
//            logger.info( "Object Persist: {}",objectMapper.writeValueAsString( emailConfirmation ) );
            emailConfirmationService.save( emailConfirmation );
//            logger.info( "Object Persist: {}",objectMapper.writeValueAsString( emailConfirmation ) );

        }

        // Se verifica que se haya insertado bien (TRUE) y lo retorna
        return isSuccessful;
    }


    /**
     * @param token
     * @return
     * @throws JsonProcessingException
     */
    @Transactional
    public String emailConfirm(String token) throws JsonProcessingException {

        Token oToken = tokenService.findById(token);
        String personId = jwtTokenUtil.getEmailWithJWTDecode(token);

        //System.out.println("email recuperado desde el token: " + personId);

        if ( oToken != null && !personId.isEmpty() ) {

            PersonToken personToken = new PersonToken();
            personToken.setPersonId(personId);
            personToken.setToken(token);
            personToken.setEnabled(true);
            personToken.setDate(timeProvider.getNow());
            personToken.setDatetime(timeProvider.getTimestamp());
//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(personToken));
            personTokenService.save( personToken );
//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(personToken));

            EmailConfirmationPK emailConfirmationPK = new EmailConfirmationPK();
            emailConfirmationPK.setPersonId( personId );
            emailConfirmationPK.setToken( token );

            EmailConfirmation emailConfirmation = emailConfirmationService.findById( emailConfirmationPK );
            emailConfirmation.setDate(timeProvider.getNow());
            emailConfirmation.setDatetime(timeProvider.getTimestamp());
            emailConfirmation.setEnabled(true);
//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(emailConfirmation));
            emailConfirmationService.update(emailConfirmation);
//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(emailConfirmation));

            Person person = personService.findById( personId );
            person.setStatus( Status.OK );
            person.setEnabled( true );
            person.setLastUpdate( timeProvider.getTimestamp() );
//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(person));
            personService.update( person );
//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(person));

        }

        return personId;
    }


    /**
     * @param person
     * @param user
     * @param response
     * @param device
     * @return
     * @throws Exception
     */
    @Transactional
    public Response update(Person person, UserUpdateForm user, Response response, Device device) throws Exception {
        try {
            AcademicInfo academicInfo = person.getAcademicInfoByAcademicInfoId();

            academicInfo.setInstitutionName(user.getInstitution());
            academicInfo.setCountryId(user.getCountry());
            academicInfo.setOccupation(user.getOccupation());
            academicInfo.setInterest(user.getInterest());

//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(academicInfo));
            academicInfoService.update(academicInfo);
//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(academicInfo));

            person.setPersonId(user.getEmail());
            person.setFirstName(user.getFirstName());
            person.setLastName(user.getLastName());
            if (!common.isEmpty(user.getPassword())) {
                person.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            person.setAcademicInfoId(academicInfo.getAcademicInfoId());
            /*person.setDate(timeProvider.getNow());
            person.setDatetime(timeProvider.getTimestamp());*/
            person.setLastUpdate(timeProvider.getTimestamp());

//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(person));
            personService.update(person);
//            logger.info("Object Persist: {}", objectMapper.writeValueAsString(person));
//            System.out.println("BIEN");
            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK);
            response.setAction(Constants.UPDATE_ACTION);
            response.setMessage("User has been updated successfully.");
        }catch (Exception e){
//            System.out.println("MUY MAL");
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setAction(Constants.UPDATE_ACTION);
            response.setMessage("Internal problems with user update.");
        }

        return response;

    }


    @Transactional
    public boolean saveLogin(Person person) throws Exception {
        boolean response = false;
        try {
            int seconds = Integer.parseInt(timeProvider.getDateSplitInfo("s"));
            //Genera un loginId
            String loginId = generateLoginId(person.getPersonId());
//            System.out.println("Login ID generado: " + loginId);
            Login login = new Login();
            login.setLoginId(loginId);
            login.setDate(timeProvider.getNow());
            login.setSeconds(seconds);
            login.setHour(timeProvider.getTimestamp());
            login.setDatetime(timeProvider.getTimestamp());
            login.setEndDate(timeProvider.getTimestamp());
            //Salva un login
            loginService.save(login);

            //Se crea la relación entre un login y una persona
            PersonLogin personLogin = new PersonLogin();
            personLogin.setPersonId(person.getPersonId());
            personLogin.setLoginId(login.getLoginId());
            personLogin.setEnabled((byte) 1);
            personLogin.setAttempts(1);
            personLoginService.save(personLogin);

        }catch (Exception e){

        }
        return response;
    }


    public String generateLoginId(String personId){
        String loginId = uniqueId.generate(25);
        Login existLogin = loginService.findById(loginId);

        if (existLogin == null){
            //Crear un nuevo login
            return loginId;
        }else{
            return generateLoginId(personId);
        }
    }


}
