package edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upm.midas.common.util.Common;
import edu.upm.midas.enums.ApiErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created by gerardo on 30/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_rest
 * @className ApiResponseError
 * @see
 */
public class ApiResponseError {

    @Autowired
    private Common common;

    private String errorCode;
    private String errorStatus;
    private String message;
    @JsonIgnore
    private String exception;
    @JsonIgnore
    private String errorDetail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Parameter parameter;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Parameter> parameters;

    public ApiResponseError(String returnCode, String returnStatus, String message, String exception, String errorSource, boolean isForTheUser, Parameter parameter){
        this.errorCode=returnCode;
        this.errorStatus=returnStatus;
        if (isForTheUser)this.message=message + ". " + errorSource;
        else this.message=message;
        this.exception=exception;
        this.errorDetail=errorSource;
        this.parameter = parameter;
    }

    public ApiResponseError(ApiErrorEnum apiEnumError, String excepcion, String errorDetail, boolean isForTheUser, Parameter parameter){
        this.errorCode=apiEnumError.getKey();
        this.errorStatus=apiEnumError.name();
        if (isForTheUser)this.message=apiEnumError.getDescription() + ". " + errorDetail;
        else this.message=apiEnumError.getDescription();
        this.exception=excepcion;
        this.errorDetail=errorDetail;
        this.parameter = parameter;
    }

    public ApiResponseError(ApiErrorEnum apiEnumError, String excepcion, String errorDetail, boolean isForTheUser, List<Parameter> parameters){
        this.errorCode=apiEnumError.getKey();
        this.errorStatus=apiEnumError.name();
        if (isForTheUser)this.message=apiEnumError.getDescription() + ". " + errorDetail;
        else this.message=apiEnumError.getDescription();
        this.exception=excepcion;
        this.errorDetail=errorDetail;
        this.parameters = parameters;
    }

    public ApiResponseError(HttpStatus httpStatus, String excepcion, String errorDetail, boolean isForTheUser, Parameter parameter){
        this.errorCode=httpStatus.toString();
        this.errorStatus=httpStatus.name();
        if (isForTheUser)this.message=httpStatus.getReasonPhrase() + ". " + errorDetail;
        else this.message=httpStatus.getReasonPhrase();
        this.exception=excepcion;
        this.errorDetail=errorDetail;
        this.parameter = parameter;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(String errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

}
