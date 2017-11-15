package edu.upm.midas.email.service;
import edu.upm.midas.email.component.EmailHtmlSender;
import edu.upm.midas.email.model.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

/**
 * Created by gerardo on 22/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className EmailService
 * @see
 */
@Service
public class EmailService {

    @Autowired
    private EmailHtmlSender emailHtmlSender;

    @Value("${email.template.confirmation}")
    private String confirmationEmailTemplate;
    @Value("${email.template.welcome}")
    private String welcomeEmailTemplate;
    @Value("${email.template.reset_password}")
    private String resetPasswordEmailTemplate;

    @Value("${email.template.confirmation.subject}")
    private String confirmationEmailSubject;
    @Value("${email.template.welcome.subject}")
    private String welcomeEmailSubject;
    @Value("${email.template.reset_password.subject}")
    private String resetPasswordEmailSubject;


    public EmailStatus sendConfirmation(String to, Context context){
        return emailHtmlSender.send(to, this.confirmationEmailSubject, this.confirmationEmailTemplate, context);
    }

    public EmailStatus sendWelcome(String to, Context context){
        return emailHtmlSender.send(to, this.welcomeEmailSubject, this.welcomeEmailTemplate, context);
    }

    public EmailStatus sendResetPassword(String to, Context context){
        return emailHtmlSender.send(to, this.resetPasswordEmailSubject, this.resetPasswordEmailTemplate, context);
    }



}
