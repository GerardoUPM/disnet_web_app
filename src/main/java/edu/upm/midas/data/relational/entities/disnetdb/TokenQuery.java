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
 * @className TokenQuery
 * @see
 */
@Entity
@Table(name = "token_query", schema = "disnetdb", catalog = "")
@IdClass(TokenQueryPK.class)
public class TokenQuery {
    private String token;
    private String queryId;
    private Date date;
    private Timestamp datetime;
    private Token tokenByToken;
    private LogQuery logQueryByQueryId;

    @Id
    @Column(name = "token", nullable = false, length = 500)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Id
    @Column(name = "query_id", nullable = false, length = 55)
    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenQuery that = (TokenQuery) o;
        return Objects.equals(token, that.token) &&
                Objects.equals(queryId, that.queryId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(datetime, that.datetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, queryId, date, datetime);
    }

    @ManyToOne
    @JoinColumn(name = "token", referencedColumnName = "token", nullable = false, insertable = false, updatable = false)
    public Token getTokenByToken() {
        return tokenByToken;
    }

    public void setTokenByToken(Token tokenByToken) {
        this.tokenByToken = tokenByToken;
    }

    @ManyToOne
    @JoinColumn(name = "query_id", referencedColumnName = "query_id", nullable = false, insertable = false, updatable = false)
    public LogQuery getLogQueryByQueryId() {
        return logQueryByQueryId;
    }

    public void setLogQueryByQueryId(LogQuery logQueryByQueryId) {
        this.logQueryByQueryId = logQueryByQueryId;
    }
}
