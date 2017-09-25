package edu.upm.midas.controller;

import edu.upm.midas.data.relational.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by gerardo on 06/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className UserController
 * @see
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView userRegister(Model model){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("countries", personService.findAllCountriesNative());
        modelAndView.setViewName("/user/registration");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String userLogin(Model model){
        return "/user/login";
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public String userRecovery(Model model){
        return "/user/forgot";
    }

}
