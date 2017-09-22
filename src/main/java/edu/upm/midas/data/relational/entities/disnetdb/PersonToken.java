package edu.upm.midas.data.relational.entities.disnetdb;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by gerardo on 12/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className PersonToken
 * @see
 */
@Entity
@Table(name = "person_token", schema = "disnetdb", catalog = "")
@IdClass(PersonTokenPK.class)
public class PersonToken {
    private String personId;
    private String token;
    private boolean enabled;
    private Date date;
    private Timestamp datetime;
    private Person personByPersonId;
    private Token tokenByToken;

    @Id
    @Column(name = "person_id", nullable = false, length = 150)
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Id
    @Column(name = "token", nullable = false, length = 500)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "datetime", nullable = true)
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonToken that = (PersonToken) o;
        return Objects.equals(personId, that.personId) &&
                Objects.equals(token, that.token) &&
                Objects.equals(enabled, that.enabled) &&
                Objects.equals(date, that.date) &&
                Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, token, enabled, date, datetime);
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
    @JoinColumn(name = "token", referencedColumnName = "token", nullable = false, insertable = false, updatable = false)
    public Token getTokenByToken() {
        return tokenByToken;
    }

    public void setTokenByToken(Token tokenByToken) {
        this.tokenByToken = tokenByToken;
    }
}
