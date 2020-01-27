package edu.upm.midas.model.jpa;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by gerardo on 27/01/2020.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className PersonConsent
 * @see
 */
@Entity
@Table(name = "person_consent", schema = "disnetdb", catalog = "")
@IdClass(PersonConsentPK.class)
public class PersonConsent {
    private String personId;
    private Integer consentId;
    private Person personByPersonId;
    private Consent consentByConsentId;

    @Id
    @Column(name = "person_id", nullable = false, length = 150)
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Id
    @Column(name = "consent_id", nullable = false)
    public Integer getConsentId() {
        return consentId;
    }

    public void setConsentId(Integer consentId) {
        this.consentId = consentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonConsent that = (PersonConsent) o;
        return Objects.equals(personId, that.personId) &&
                Objects.equals(consentId, that.consentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(personId, consentId);
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false, insertable = false, updatable = false)
    public Person getPersonByPersonId() {
        return personByPersonId;
    }

    public void setPersonByPersonId(Person personByPersonId) {
        this.personByPersonId = personByPersonId;
    }

    @ManyToOne
    @JoinColumn(name = "consent_id", referencedColumnName = "consent_id", nullable = false, insertable = false, updatable = false)
    public Consent getConsentByConsentId() {
        return consentByConsentId;
    }

    public void setConsentByConsentId(Consent consentByConsentId) {
        this.consentByConsentId = consentByConsentId;
    }
}
