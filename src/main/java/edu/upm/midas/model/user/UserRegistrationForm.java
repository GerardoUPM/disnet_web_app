package edu.upm.midas.model.user;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by gerardo on 20/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className UserRegistrationForm
 * @see
 */
public class UserRegistrationForm {

    @NotNull
    @Length(min = 1, max = 55, message = "Your name must not exceed 50 characters")
    @NotEmpty(message = "*Please provide your name")
    private String firstName;
    @NotNull
    @Length(min = 1, max = 55, message = "Your last name must not exceed 90 characters")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;
    @NotNull
    @Length(min = 5, max = 150, message = "Your email must not exceed 150 characters")
    @NotEmpty(message = "*Please provide your email")
    @Email(message = "*Please provide a valid email")
    private String email;
    @NotNull
    @Length(min = 8, max = 30, message = "Your password must have at least 8 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    @NotNull
    @Length(min = 1, max = 200, message = "Your institution name must not exceed 200 characters")
    @NotEmpty(message = "*Please provide your institution name")
    private String institution;
    //@NotNull
    //@NotEmpty(message = "*Please provide your country")
    private int country;
    @NotNull
    @Length(min = 1, max = 200, message = "Your occupation must not exceed 50 characters")
    @NotEmpty(message = "*Please provide your occupation")
    private String occupation;
    @NotNull
    @Length(min = 1, max = 200, message = "Your insterest must not exceed 2000 characters")
    @NotEmpty(message = "*Please provide your insterest")
    private String interest;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "UserRegistrationForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", institution='" + institution + '\'' +
                ", country=" + country +
                ", occupation='" + occupation + '\'' +
                ", interest='" + interest + '\'' +
                '}';
    }
}
