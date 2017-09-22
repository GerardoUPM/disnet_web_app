package edu.upm.midas.data.relational.entities.disnetdb;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 12/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className Token
 * @see
 */
@Entity
public class Token {
    private String token;
    private String type;
    private boolean enable;
    private String expire;
    private String scope;
    private Date date;
    private Timestamp datetime;
    private Timestamp lastUpdate;
    private List<EmailConfirmation> emailConfirmationsByToken;
    private List<PersonToken> personTokensByToken;
    private List<TokenQuery> tokenQueriesByToken;

    @Id
    @Column(name = "token", nullable = false, length = 500)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 10)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "enable", nullable = false)
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "expire", nullable = true, length = -1)
    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    @Basic
    @Column(name = "scope", nullable = true, length = 45)
    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "datetime", nullable = false)
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "last_update", nullable = false)
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp update) {
        this.lastUpdate = update;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token) &&
                Objects.equals(type, token1.type) &&
                Objects.equals(enable, token1.enable) &&
                Objects.equals(expire, token1.expire) &&
                Objects.equals(scope, token1.scope) &&
                Objects.equals(date, token1.date) &&
                Objects.equals(datetime, token1.datetime) &&
                Objects.equals(lastUpdate, token1.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type, enable, expire, scope, date, datetime, lastUpdate);
    }

    @OneToMany(mappedBy = "tokenByToken")
    public List<EmailConfirmation> getEmailConfirmationsByToken() {
        return emailConfirmationsByToken;
    }

    public void setEmailConfirmationsByToken(List<EmailConfirmation> emailConfirmationsByToken) {
        this.emailConfirmationsByToken = emailConfirmationsByToken;
    }

    @OneToMany(mappedBy = "tokenByToken")
    public List<PersonToken> getPersonTokensByToken() {
        return personTokensByToken;
    }

    public void setPersonTokensByToken(List<PersonToken> personTokensByToken) {
        this.personTokensByToken = personTokensByToken;
    }

    @OneToMany(mappedBy = "tokenByToken")
    public List<TokenQuery> getTokenQueriesByToken() {
        return tokenQueriesByToken;
    }

    public void setTokenQueriesByToken(List<TokenQuery> tokenQueriesByToken) {
        this.tokenQueriesByToken = tokenQueriesByToken;
    }
}
