package edu.upm.midas.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gerardo on 04/06/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className MemberController
 * @see
 */
@Controller
public class MemberController {

    @RequestMapping("/members")
    public String membersPage(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members";
    }

    @RequestMapping("/members/alejandro")
    public String alejandroMember(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/alejandro";
    }

    @RequestMapping("/members/eduardo")
    public String eduardoMenber(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/eduardo";
    }

    @RequestMapping("/members/gerardo")
    public String gerardoMenber(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/gerardo";
    }


    @RequestMapping("/members/lucia")
    public String luciaMenber(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/lucia";
    }

    @RequestMapping("/members/massimiliano")
    public String massimilianoMenber(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/massimiliano";
    }


}
