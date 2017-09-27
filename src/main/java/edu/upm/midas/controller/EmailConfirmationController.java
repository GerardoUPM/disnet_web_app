package edu.upm.midas.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.upm.midas.data.relational.service.helper.PersonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by gerardo on 25/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className ConfirmationController
 * @see
 */
@Controller
public class EmailConfirmationController {

    @Autowired
    private PersonHelper personHelper;

    @RequestMapping(value = "/confirmation_email", method = RequestMethod.GET, params = {"token"})
    public ModelAndView confirmationEmail(@RequestParam(value = "token") String token, Device device) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("Se va a confirmar el mail");
        String personId = personHelper.emailConfirm( token );

        if (!personId.isEmpty()) {System.out.println("Entra success");
            modelAndView.addObject("successMessage", "Congratulation!. Your disnet account has been successfully confirmed with the email address " + personId);
            //modelAndView.addObject("personId", personId);
            modelAndView.setViewName("/user/confirmation_email_response");
        }else{
            modelAndView.addObject("errorMessage", "A problem has occurred with your account confirmation. Please contact the application administrator");
            //modelAndView.addObject("personId", personId);
            modelAndView.setViewName("/user/confirmation_email_response");
        }

        return modelAndView;
    }

}
