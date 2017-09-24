package edu.upm.midas.controller;

import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.relational.entities.disnetdb.SystemParameter;
import edu.upm.midas.email.component.EmailHtmlSender;
import edu.upm.midas.email.model.EmailStatus;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;

import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gerardo on 21/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className EmailController
 * @see
 */
@Controller
public class EmailController {

    @Autowired
    private EmailHtmlSender emailHtmlSender;
    @Autowired
    private Constants constants;

    @Autowired
    private Configuration freemarkerConfig;

    @RequestMapping(value = "/user/test", method = RequestMethod.GET)
    @ResponseBody
    String home() {
        try {
            Context context = new Context();
            context.setVariable("user", "Gerardo Lagunes");
            context.setVariable("email","grardolagar@gmail.com");
            context.setVariable("token","http://algo.com?token=987987d98f79fv7d8v798fv79s8v7s9879s0s7s0879df87v90sd7f90d7ff07707ds07v7707vsd7vfds07vd98f7d87vdf8s9078707vs0f77s0v870d9sv0f90s7dsv07df0v7f070sd70sv7f0sd7vs0d7f07v7");
            System.out.print("head: "+constants.HTTPS_HEADER);
            EmailStatus emailStatus = emailHtmlSender.send("grardolagar@hotmail.com", "Title of email", "email/confirmation_email", context);
//            sendEmail();
            return "Email Sent!";
        } catch (Exception ex) {
            return "Error in sending email: " + ex;
        }
    }


}
