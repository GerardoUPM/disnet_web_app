package edu.upm.midas.model.jpa;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PersonConsent.findAll", query = "SELECT p FROM PersonConsent p")
        , @NamedQuery(name = "PersonConsent.findById", query = "SELECT p FROM PersonConsent p WHERE p.personId = :personId AND p.consentId = :consentId")
        , @NamedQuery(name = "PersonConsent.findByPersonId", query = "SELECT p FROM PersonConsent p WHERE p.personId = :personId")
        , @NamedQuery(name = "PersonConsent.findByConsentId", query = "SELECT p FROM PersonConsent p WHERE p.consentId = :consentId")
        })

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "PersonConsent.findByIdNative",
                query = "SELECT p.person_id, p.consent_id " +
                        "FROM person_consent p " +
                        "WHERE p.person_id = :personId AND p.consent_id = :consentId "
        ),

        @NamedNativeQuery(
                name = "PersonConsent.insertNative",
                query = "INSERT INTO person_consent (person_id, consent_id) " +
                        "VALUES ( :personId, :consentId) "
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "PersonConsentMapping",
                entities = @EntityResult(
                        entityClass = PersonConsent.class,
                        fields = {
                                @FieldResult(name = "personId", column = "person_id"),
                                @FieldResult(name = "consentId", column = "consent_id")
                        }
                )
        )
})
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
