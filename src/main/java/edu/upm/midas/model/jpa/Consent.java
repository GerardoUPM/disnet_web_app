package edu.upm.midas.model.jpa;
import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by gerardo on 27/01/2020.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className Consent
 * @see
 */
@Entity
public class Consent {
    private Integer consentId;
    private String description;
    private String formName;
    private Collection<PersonConsent> personConsentsByConsentId;

    @Id
    @Column(name = "consent_id", nullable = false)
    public Integer getConsentId() {
        return consentId;
    }

    public void setConsentId(Integer consentId) {
        this.consentId = consentId;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "form_name", nullable = true, length = 45)
    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consent consent = (Consent) o;
        return Objects.equals(consentId, consent.consentId) &&
                Objects.equals(description, consent.description) &&
                Objects.equals(formName, consent.formName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(consentId, description, formName);
    }

    @OneToMany(mappedBy = "consentByConsentId")
    public Collection<PersonConsent> getPersonConsentsByConsentId() {
        return personConsentsByConsentId;
    }

    public void setPersonConsentsByConsentId(Collection<PersonConsent> personConsentsByConsentId) {
        this.personConsentsByConsentId = personConsentsByConsentId;
    }
}
