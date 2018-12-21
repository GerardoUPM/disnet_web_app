package edu.upm.midas.enums;

import org.apache.commons.lang.StringUtils;

public enum ApiErrorEnum {

	INVALID_PARAMETERS("400", "A required parameter for this API operation is invalid or has not been provided"),
	UNAUTHORIZED("401", "The access token credential are missing or invalid for the given request"),
	RESOURCE_NOT_FOUND("404", "Could not find resource"),
	RESOURCES_NOT_FOUND("404", "Could not find resources"),
	INTERNAL_SERVER_ERROR("500", "An unknown Api exception was thrown");

	private String key;
	private String description;

	private ApiErrorEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}
	
	public static ApiErrorEnum getEnum(String key) {
		if (StringUtils.isNotBlank(key)) {
	        for (ApiErrorEnum status : ApiErrorEnum.values()) {
	            if (key.equals(status.getKey()))
	                return status;
	        }
        }
		return null;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
