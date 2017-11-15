package edu.upm.midas.model.user;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by gerardo on 14/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className RequestResetPassword
 * @see
 */
public class RequestResetPassword {

    @NotNull
    @Length(min = 5, max = 150, message = "Your email must not exceed 150 characters")
    @NotEmpty(message = "*Please provide your email")
    @Email(message = "*Please provide a valid email")
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
