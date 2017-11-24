package edu.upm.midas.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.token.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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
    public String indexPage(Model model, HttpSession sesion){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        System.out.println("AttributeNames: "+sesion.getAttributeNames() + " ID: " + sesion.getId() + " isNew: " + sesion.isNew() + " ServletContext: " + sesion.getServletContext() + " CreationTime: " + sesion.getCreationTime());
        return "index";
    }

    @RequestMapping("/apis/tvp")
    public String tvpApiPage(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/tvp";
    }

    @RequestMapping("/apis/disnet")
    public String disnetApiPage(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disnet";
    }

    @RequestMapping("/apis/disease_album")
    public String diseaseAlbumApiPage(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "apis/disease_album";
    }

}
