package edu.upm.midas.controller;

import edu.upm.midas.client_modules.disnet_query.texts_extraction.api_response.DisnetService;
import edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response.analysis.DatabaseStatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.token.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        //IMPORTANTE PARA SABER LA SESIÓN
        //System.out.println("AttributeNames: "+sesion.getAttributeNames() + " ID: " + sesion.getId() + " isNew: " + sesion.isNew() + " ServletContext: " + sesion.getServletContext() + " CreationTime: " + sesion.getCreationTime());
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //System.out.println("SE PUEDE: " + auth.getName() + " PERSON: " + sesion.getAttribute("person") + " TOKEN: " + sesion.getAttribute("token"));
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


    @RequestMapping("/temporarily_disabled")
    public String temporarilyDisabledPage(Model model){
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println("PWD: " + passwordEncoder.encode("groot"));
        return "temporarily_disabled";
    }
}
