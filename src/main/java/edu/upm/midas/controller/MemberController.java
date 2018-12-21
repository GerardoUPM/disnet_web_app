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

    @RequestMapping("/members/about")
    public String membersPage(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/about";
    }

    @RequestMapping("/members/alejandro")
    public String alejandroMember(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/alejandro";
    }

    @RequestMapping("/members/eduardo")
    public String eduardoMember(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/eduardo";
    }

    @RequestMapping("/members/ernestina")
    public String ernestinaMember(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/ernestina";
    }

    @RequestMapping("/members/gerardo")
    public String gerardoMember(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/gerardo";
    }


    @RequestMapping("/members/lucia")
    public String luciaMember(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/lucia";
    }

    @RequestMapping("/members/massimiliano")
    public String massimilianoMember(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "members/massimiliano";
    }


}
