package edu.upm.midas.model.jpa;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by gerardo on 27/01/2020.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className PersonConsentPK
 * @see
 */
public class PersonConsentPK implements Serializable {
    private String personId;
    private Integer consentId;

    @Column(name = "person_id", nullable = false, length = 150)
    @Id
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Column(name = "consent_id", nullable = false)
    @Id
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
        PersonConsentPK that = (PersonConsentPK) o;
        return Objects.equals(personId, that.personId) &&
                Objects.equals(consentId, that.consentId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(personId, consentId);
    }
}
