package edu.upm.midas.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.token.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gerardo on 12/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className IndexController
 * @see
 */
@Controller
public class IndexController {



    @RequestMapping("/")
    public String indexPage(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "index";
    }

}
