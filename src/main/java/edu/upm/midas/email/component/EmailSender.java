package edu.upm.midas.email.component;
import edu.upm.midas.email.model.EmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by gerardo on 22/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className EmailSender
 * @see
 */
@Component
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${email.template.from.noreply.address}")
    private String noReplyAddress;
    @Value("${email.template.from.noreply.personal}")
    private String noReplyPersonal;

    public EmailStatus sendPlainText(String to, String subject, String text) {
        return sendM(to, subject, text, false);
    }

    public EmailStatus sendHtml(String to, String subject, String htmlBody) {
        return sendM(to, subject, htmlBody, true);
    }

    private EmailStatus sendM(String to, String subject, String text, Boolean isHtml) {/*, MultipartFile image*/
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            InternetAddress address = new InternetAddress(this.noReplyAddress, this.noReplyPersonal);

            //mail.setSender(address);
            //mail.setFrom(address);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            helper.setFrom(address);
/*
            InputStreamSource imageSource = new ByteArrayResource(image.getBytes());
            helper.addInline(image.getName(), imageSource, image.getContentType());
*/
            javaMailSender.send(mail);
            LOGGER.info("Send email '{}' to: {}", subject, to);
            return new EmailStatus(to, subject, text).success();
        } catch (Exception e) {
            LOGGER.error(String.format("Problem sending email to: {%s}, error message: {%s}", to, e.getMessage()));
            LOGGER.error("SEND EMAIL ERROR", e);
            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    }

}
