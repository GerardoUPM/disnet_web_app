package edu.upm.midas.model.user;
import edu.upm.midas.common.utils.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by gerardo on 28/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_web_app
 * @className TransactionHistory
 * @see
 */
public class TransactionHistory {

    private String transactionId;
    private String request;
    private java.sql.Date date;
    private Timestamp datetime;
    private String datetimeFormat;
    private Timestamp startDatetime;
    private Timestamp endDatetime;
    private String runtime;
    private String runtime_milliseconds;


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
        this.datetimeFormat =
                dateFormatyyyMMdd(datetime);
    }

    public String getDatetimeFormat() {
        return datetimeFormat;
    }

    public Timestamp getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Timestamp startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Timestamp getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Timestamp endDatetime) {
        this.endDatetime = endDatetime;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getRuntime_milliseconds() {
        return runtime_milliseconds;
    }

    public void setRuntime_milliseconds(String runtime_milliseconds) {
        this.runtime_milliseconds = runtime_milliseconds;
    }

    public String dateFormatyyyMMdd(java.util.Date date){//System.out.println(date+"hola");
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
