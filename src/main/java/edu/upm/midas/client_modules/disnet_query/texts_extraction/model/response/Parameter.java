package edu.upm.midas.client_modules.disnet_query.texts_extraction.model.response;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by gerardo on 01/12/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project disnet_rest
 * @className Parameter
 * @see
 */
public class Parameter {

    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean required;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean defaultValue;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public Parameter(String name) {
        this.name = name;
    }

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Parameter(String name, String value, String message) {
        this.name = name;
        this.value = value;
        this.message = message;
    }

    public Parameter(String name, Boolean required, Boolean defaultValue, String value, String message) {
        this.name = name;
        this.required = required;
        this.defaultValue = defaultValue;
        this.value = value;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
