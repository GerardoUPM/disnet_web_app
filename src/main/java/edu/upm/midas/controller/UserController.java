package edu.upm.midas.controller;
import edu.upm.midas.common.utils.TimeProvider;
import edu.upm.midas.common.utils.UniqueId;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.relational.entities.disnetdb.Person;
import edu.upm.midas.data.relational.service.CountryService;
import edu.upm.midas.data.relational.service.LogQuery_Service;
import edu.upm.midas.data.relational.service.PersonService;
import edu.upm.midas.data.relational.service.PersonTokenService;
import edu.upm.midas.data.relational.service.helper.PersonHelper;
import edu.upm.midas.email.model.EmailStatus;
import edu.upm.midas.email.service.EmailService;
import edu.upm.midas.model.user.RequestResetPassword;
import edu.upm.midas.model.user.Response;
import edu.upm.midas.model.user.TransactionHistory;
import edu.upm.midas.model.user.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by gerardo on 13/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className UserController
 * @see
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PersonHelper personHelper;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private PersonService personService;
    @Autowired
    private LogQuery_Service logQuery_service;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TimeProvider timeProvider;
    @Autowired
    private PersonTokenService personTokenService;
    @Autowired
    private CountryService countryService;


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Response updateUser(@RequestBody @Valid UserUpdateForm userRegistrationForm, Device device) throws Exception {

        //System.out.println("UPDATE: " + userRegistrationForm.toString());
        Response response = new Response();
        Person person = personHelper.findByEmailAndStatusOK(userRegistrationForm.getEmail());

        if (person != null) {
            return personHelper.update(person, userRegistrationForm, response, device);
        } else {
            //System.out.println("NO SE ENCONTRO EL USUARIO");
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setAction(Constants.UPDATE_ACTION);
            response.setMessage("There is not a user registered with the email provided.");
        }
        return response;
    }

    @RequestMapping(value = "/reset_password", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response resetPersonPassword(@RequestBody @Valid RequestResetPassword request, BindingResult bindingResult, Device device) throws Exception {
        Response response = new Response();
        Person person = personHelper.findByEmailAndStatusOK(request.getEmail());

        if (person != null) {
            try {
                String newPassword = uniqueId.generate(12);
                System.out.println("new: " + newPassword);
                person.setPassword(bCryptPasswordEncoder.encode(newPassword));
                person.setLastUpdate(timeProvider.getTimestamp());
                personService.update(person);
                //Enviar email con nueva contraseña
                Context context = new Context();
                context.setVariable("user", person.getFirstName() + " " + person.getLastName());
                context.setVariable("password", newPassword);
                EmailStatus resetPasswordEmailStatus = emailService.sendResetPassword(person.getPersonId(), context);
                response.setCode(HttpStatus.OK.value());
                response.setStatus(HttpStatus.OK);
                response.setAction(Constants.UPDATE_ACTION);
                response.setMessage("Password has been reset successfully.");
            } catch (Exception e) {
                response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                response.setAction(Constants.UPDATE_ACTION);
                response.setMessage("Internal problems with password reset.");
            }
        } else {
            System.out.println("NO SE ENCONTRO EL USUARIO");
            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setAction(Constants.UPDATE_ACTION);
            response.setMessage("There is not a user registered with the email provided.");
        }
        return response;
    }


    @RequestMapping(value = "/request_history", method = RequestMethod.GET)
    public List<TransactionHistory> requestHistoryByPersonAndToken(HttpSession sesion) throws Exception {
         return logQuery_service.findByTokenNative(sesion.getAttribute("token").toString());
        /*for (TransactionHistory transaction: transactionHistories) {
            System.out.println("TRAN=> " + transaction.getTransactionId() + " | " + transaction.getRequest() + " | " + transaction.getDate() + " | " +transaction.getDatetime()+ " | " +transaction.getStartDatetime()+ " | " +transaction.getEndDatetime()+ " | " +transaction.getRuntime()+ " | " +transaction.getRuntime_milliseconds());
        }*/
    }
}
