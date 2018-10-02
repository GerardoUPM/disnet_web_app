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
public class DisnetApiController {

    @RequestMapping("/apis/disnet/web_service_1")
    public String webService1(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_1";
    }

    @RequestMapping("/apis/disnet/web_service_2")
    public String webService2(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_2";
    }

    @RequestMapping("/apis/disnet/web_service_3")
    public String webService3(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_3";
    }

    @RequestMapping("/apis/disnet/web_service_4")
    public String webService4(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_4";
    }

    @RequestMapping("/apis/disnet/web_service_5")
    public String webService5(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_5";
    }

    @RequestMapping("/apis/disnet/web_service_6")
    public String webService6(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_6";
    }

    @RequestMapping("/apis/disnet/web_service_7")
    public String webService7(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_7";
    }

    @RequestMapping("/apis/disnet/web_service_8")
    public String webService8(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_8";
    }

    @RequestMapping("/apis/disnet/web_service_9")
    public String webService9(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_9";
    }

    @RequestMapping("/apis/disnet/web_service_10")
    public String webService10(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_10";
    }

    @RequestMapping("/apis/disnet/web_service_11")
    public String webService11(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/web_service_11";
    }


    @RequestMapping("/apis/disnet/common_errors")
    public String commonErrors(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet/common_errors";
    }

}
