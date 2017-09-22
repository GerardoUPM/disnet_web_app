package edu.upm.midas.data.relational.entities.disnetdb;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 12/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project web_acces_control
 * @className LogQuery
 * @see
 */
@Entity
@Table(name = "log_query", schema = "disnetdb", catalog = "")
public class LogQuery {
    private String queryId;
    private String status;
    private String query;
    private Byte success;
    private String page;
    private String request;
    private String response;
    private Timestamp startDate;
    private Timestamp endDate;
    private List<LogQueryService> logQueryServicesByQueryId;
    private List<LogQueryUrl> logQueryUrlsByQueryId;
    private List<TokenQuery> tokenQueriesByQueryId;

    @Id
    @Column(name = "query_id", nullable = false, length = 55)
    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    @Basic
    @Column(name = "status", nullable = false, length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "query", nullable = false, length = -1)
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Basic
    @Column(name = "success", nullable = false)
    public Byte getSuccess() {
        return success;
    }

    public void setSuccess(Byte success) {
        this.success = success;
    }

    @Basic
    @Column(name = "page", nullable = true, length = 100)
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Basic
    @Column(name = "request", nullable = true, length = -1)
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    @Basic
    @Column(name = "response", nullable = true, length = -1)
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogQuery logQuery = (LogQuery) o;
        return Objects.equals(queryId, logQuery.queryId) &&
                Objects.equals(status, logQuery.status) &&
                Objects.equals(query, logQuery.query) &&
                Objects.equals(success, logQuery.success) &&
                Objects.equals(page, logQuery.page) &&
                Objects.equals(request, logQuery.request) &&
                Objects.equals(response, logQuery.response) &&
                Objects.equals(startDate, logQuery.startDate) &&
                Objects.equals(endDate, logQuery.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryId, status, query, success, page, request, response, startDate, endDate);
    }

    @OneToMany(mappedBy = "logQueryByQueryId")
    public List<LogQueryService> getLogQueryServicesByQueryId() {
        return logQueryServicesByQueryId;
    }

    public void setLogQueryServicesByQueryId(List<LogQueryService> logQueryServicesByQueryId) {
        this.logQueryServicesByQueryId = logQueryServicesByQueryId;
    }

    @OneToMany(mappedBy = "logQueryByQueryId")
    public List<LogQueryUrl> getLogQueryUrlsByQueryId() {
        return logQueryUrlsByQueryId;
    }

    public void setLogQueryUrlsByQueryId(List<LogQueryUrl> logQueryUrlsByQueryId) {
        this.logQueryUrlsByQueryId = logQueryUrlsByQueryId;
    }

    @OneToMany(mappedBy = "logQueryByQueryId")
    public List<TokenQuery> getTokenQueriesByQueryId() {
        return tokenQueriesByQueryId;
    }

    public void setTokenQueriesByQueryId(List<TokenQuery> tokenQueriesByQueryId) {
        this.tokenQueriesByQueryId = tokenQueriesByQueryId;
    }
}
