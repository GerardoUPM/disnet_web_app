package edu.upm.midas.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.upm.midas.data.relational.entities.disnetdb.Person;
import edu.upm.midas.data.relational.service.helper.PersonHelper;
import edu.upm.midas.email.component.EmailHtmlSender;
import edu.upm.midas.email.model.EmailStatus;
import edu.upm.midas.email.service.EmailService;
import edu.upm.midas.model.user.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import javax.validation.Valid;

/**
 * Created by gerardo on 21/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className LoginController
 * @see
 */
@Controller
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private PersonHelper personHelper;


    @RequestMapping(value="/client/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        Person user = personHelper.findByEmailAndStatusOK( auth.getName() );
        System.out.println(user.toString());
        modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getPersonId() + ")");
        modelAndView.addObject("clientMessage","Content Available Only for Users with USER Role");
        modelAndView.setViewName("user/client/home");
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserRegistrationForm userRegistrationForm, BindingResult bindingResult, Device device) throws JsonProcessingException {
        System.out.println(userRegistrationForm.toString());
        ModelAndView modelAndView = new ModelAndView();

        Person userExists = personHelper.findByEmailAndStatusOK( userRegistrationForm.getEmail() );

        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/user/registration");
        } else {
            if ( personHelper.saveNewUser(userRegistrationForm, device) ){
                System.out.println("BIEN");
                modelAndView.addObject("successMessage", "User has been registered successfully");
                modelAndView.addObject("user", userRegistrationForm);
                modelAndView.setViewName("/user/confirmation");
            }else{
                System.out.println("MAL");
                modelAndView.addObject("errorMessage", "Problems registering user");
                modelAndView.addObject("user", userRegistrationForm);
                modelAndView.setViewName("/user/confirmation");
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "/confirmation_email", method = RequestMethod.GET)
    public ModelAndView confirmationEmail(@Valid String token, Device device) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView();

        String personId = personHelper.emailConfirm( token );

        if (!personId.isEmpty()) {
            modelAndView.addObject("successMessage", "Congratulation!. Your disnet account has been successfully confirmed with the email address " + personId);
            modelAndView.addObject("personId", personId);
            modelAndView.setViewName("/user/confirmation_email_response");
        }else{
            modelAndView.addObject("errorMessage", "A problem has occurred with your account confirmation. Please contact the application administrator");
            modelAndView.addObject("personId", personId);
            modelAndView.setViewName("/user/confirmation_email_response");
        }

        return modelAndView;
    }


}
