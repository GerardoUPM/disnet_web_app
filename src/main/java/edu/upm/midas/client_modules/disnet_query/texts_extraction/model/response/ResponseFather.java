package edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by gerardo on 02/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project get_diseases_list_rest
 * @className ResponseFather
 * @see
 */
public class ResponseFather {

    private String token;
    private boolean authorized;
    private String authorizationMessage;

    private String responseCode;
    private String responseMessage;
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiResponseError> errorsFound;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Parameter> extraInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String infoToken;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getAuthorizationMessage() {
        return authorizationMessage;
    }

    public void setAuthorizationMessage(String authorizationMessage) {
        this.authorizationMessage = authorizationMessage;
    }


    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public List<ApiResponseError> getErrorsFound() {
        return errorsFound;
    }

    public void setErrorsFound(List<ApiResponseError> errorsFound) {
        this.errorsFound = errorsFound;
    }

    public List<Parameter> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(List<Parameter> extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getInfoToken() {
        return infoToken;
    }

    public void setInfoToken(String infoToken) {
        this.infoToken = infoToken;
    }
}
