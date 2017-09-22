package edu.upm.midas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String userRegister(Model model){
        return "/user/registration";
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
