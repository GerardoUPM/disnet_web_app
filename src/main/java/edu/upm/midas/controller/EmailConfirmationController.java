package edu.upm.midas.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
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
    private edu.upm.midas.service.jpa.helper.PersonHelper personHelper;

    @RequestMapping(value = "/confirmation_email", method = RequestMethod.GET, params = {"token"})
    public ModelAndView confirmationEmail(@RequestParam(value = "token") String token, Device device) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //System.out.println("Se va a confirmar el mail");

        try {
            String personId = personHelper.emailConfirm(token);

            if (!personId.isEmpty()) {
                //System.out.println("Entra success");
                modelAndView.addObject("successMessage", "Congratulation!. Your disnet account has been successfully confirmed with the email address " + personId);
                //modelAndView.addObject("personId", personId);
                modelAndView.setViewName("user/confirmation_email_response");
            } else {
                modelAndView.addObject("errorMessage", "A problem has occurred with your account confirmation. Please contact the administrator");
                //modelAndView.addObject("personId", personId);
                modelAndView.setViewName("user/confirmation_email_response");
            }
        }catch (Exception e){
            modelAndView.addObject("errorMessage", "There has been a problem validating the email. Check if your account is already validated by logging in or please contact the administrator");
            //modelAndView.addObject("personId", personId);
            modelAndView.setViewName("user/confirmation_email_response");
        }

        return modelAndView;
    }

}
