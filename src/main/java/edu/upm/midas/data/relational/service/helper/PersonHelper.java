package edu.upm.midas.data.relational.service.helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.common.utils.TimeProvider;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.relational.entities.disnetdb.*;
import edu.upm.midas.data.relational.service.*;
import edu.upm.midas.email.service.EmailService;
import edu.upm.midas.model.user.UserRegistrationForm;
import edu.upm.midas.token.model.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

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
    private Constants constants;

    @Autowired
    private PersonService personService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * @param email
     * @return
     */
    public Person findByEmailAndStatusOK(String email){
        Object[] person = personService.findByIdAndStatusNative(email, "OK");
        return createPerson(person);
    }


    /**
     * @param email
     * @return
     */
    public Person findByEmailAndStatusNW(String email){
        Object[] person = personService.findByIdAndStatusNative(email, "NW");
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
            user.setLastUpdate((Timestamp) person[16]);

            Profile profile = new Profile();
            profile.setProfileId((String) person[6]);
            profile.setName((String) person[7]);
            profile.setAuthority( getAuthority( (String) person[8] ) );

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
            if (stat.equals(status.trim())) statusFound = stat;
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

    @Transactional
    public boolean saveNewUser(UserRegistrationForm user, Device device) throws JsonProcessingException {
        AcademicInfo academicInfo = new AcademicInfo();
        academicInfo.setInstitutionName( user.getInstitution() );
        academicInfo.setCountryId( user.getCountry() );
        academicInfo.setOccupation( user.getOccupation() );
        academicInfo.setInterest( user.getInterest() );

        logger.info( "Object Persist: {}",objectMapper.writeValueAsString(academicInfo) );
        //academicInfoService.save(academicInfo);
        logger.info( "Object Persist: {}",objectMapper.writeValueAsString(academicInfo) );

        Person person = new Person();
        person.setPersonId( user.getEmail() );
        person.setStatus( Status.NW );
        person.setEnabled( false );
        person.setFirstName( user.getFirstName() );
        person.setLastName( user.getLastName() );
        person.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        person.setProfileId("USER");
        person.setAcademicInfoId( academicInfo.getAcademicInfoId() );
        person.setDate(timeProvider.getNow());
        person.setDatetime(timeProvider.getTimestamp());
        person.setLastUpdate(timeProvider.getTimestamp());

        logger.info( "Object Persist: {}",objectMapper.writeValueAsString(person) );
        //personService.save(person);
        logger.info( "Object Persist: {}",objectMapper.writeValueAsString(person) );

        boolean isSuccessful = true;//(findByEmailAndStatusNW( user.getEmail() ) != null );

        //Enviar correo de confirmación
        if (isSuccessful){

            //<editor-fold desc="GENERACIÓN DE TOKEN">
            String token = jwtTokenUtil.generateToken(person, device);
            System.out.println(token);

            Token oToken = new Token();
            oToken.setToken(token);
            oToken.setType("C");
            oToken.setEnabled(true);
            oToken.setExpiration(0);
            oToken.setScope("");
            oToken.setDate(timeProvider.getNow());
            oToken.setDatetime(timeProvider.getTimestamp());
            oToken.setLastUpdate(timeProvider.getTimestamp());

            logger.info( "Object Persist: {}",objectMapper.writeValueAsString(oToken) );
            tokenService.save(oToken);
            logger.info( "Object Persist: {}",objectMapper.writeValueAsString(oToken) );

            //</editor-fold>

            Context context = new Context();
            context.setVariable("user", person.getFirstName() + " " + person.getLastName());
            context.setVariable("email", person.getPersonId());
            context.setVariable("token", token);
            emailService.sendConfirmation( person.getPersonId(), context );
        }

        // Se verifica que se haya insertado bien (TRUE) y lo retorna
        return isSuccessful;
    }


}
