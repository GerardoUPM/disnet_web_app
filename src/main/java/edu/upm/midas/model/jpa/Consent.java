package edu.upm.midas.model.jpa;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
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
@Table(name = "consent", schema = "disnetdb", catalog = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Consent.findAll", query = "SELECT a FROM Consent a")
        , @NamedQuery(name = "Consent.findByConsentId", query = "SELECT a FROM Consent a WHERE a.consentId = :consentId")
        , @NamedQuery(name = "Consent.findByDescription", query = "SELECT a FROM Consent a WHERE a.description = :description")
        , @NamedQuery(name = "Consent.findByFormName", query = "SELECT a FROM Consent a WHERE a.formName = :formName")
        , @NamedQuery(name = "Consent.findByFormNameLike", query = "SELECT a FROM Consent a WHERE a.formName LIKE CONCAT('%',:formName,'%')")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Consent.findByIdNative",
                query = "SELECT c.consent_id, c.description, c.form_name\n" +
                        "FROM consent c\n" +
                        "WHERE c.consent_id = :consentId "
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "ConsentMapping",
                entities = @EntityResult(
                        entityClass = Consent.class,
                        fields = {
                                @FieldResult(name = "consentId", column = "consent_id"),
                                @FieldResult(name = "description", column = "description")
                        }
                )
        )
})

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="consentId")
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

    @Override
    public String toString() {
        return "Consent{" +
                "consentId=" + consentId +
                ", description='" + description + '\'' +
                ", formName='" + formName + '\'' +
                ", personConsentsByConsentId=" + personConsentsByConsentId +
                '}';
    }
}
