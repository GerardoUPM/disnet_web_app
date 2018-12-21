package edu.upm.midas.model.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class UserLogin {
    @NotNull
    @Length(min = 5, max = 150, message = "Your email must not exceed 150 characters")
    @NotEmpty(message = "*Please provide your email")
    @Email(message = "*Please provide a valid email")
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
