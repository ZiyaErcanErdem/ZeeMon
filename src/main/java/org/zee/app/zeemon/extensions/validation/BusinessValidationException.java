package org.zee.app.zeemon.extensions.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class BusinessValidationException extends RuntimeException {

	private static final long serialVersionUID = 1999445721841283571L;
	private static final String appName = "evaCoreApp";
	private String entity;
	private String notificationType;
	private ValidationMessageKey messageKey;
	private Map<String, Object> params;

	public BusinessValidationException(ValidationMessageKey messageKey) {
		super();
		this.messageKey = messageKey;
		this.notificationType = "toast";
	}

	public BusinessValidationException(ValidationMessageKey messageKey, String message) {
		super(message);
		this.messageKey = messageKey;
	}

	public BusinessValidationException(ValidationMessageKey messageKey, Throwable cause) {
		super(cause);
		this.messageKey = messageKey;
	}

	public BusinessValidationException(ValidationMessageKey messageKey, String message, Throwable cause) {
		super(message, cause);
		this.messageKey = messageKey;
	}

	public BusinessValidationException(ValidationMessageKey messageKey, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.messageKey = messageKey;
	}
	
	public BusinessValidationException withEntity(String entityName) {
		this.entity = entityName;
		return this;
	}
	
	public BusinessValidationException withNotificationType(String notificationType) {
		this.notificationType = notificationType;
		return this;
	}
	
	public BusinessValidationException withParam(String key, Object value) {
		if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
			return this;
		}
		if(null == this.params) {
			this.params = new HashMap<>();
		}
		this.params.put(key, value);
		return this;
	}
	
	public ValidationMessageKey getMessageKey() {
		return this.messageKey;
	}

	public Map<String, Object> getParams() {
		return this.params;
	}
	
	public String getMessageKeyText() {
		if(StringUtils.isEmpty(this.entity)) {
			return appName + ".notification." + this.messageKey.name();
		} else {
			return appName + "." + this.entity + ".notification." + this.messageKey.name();
		}
	}
	
	public String getNotificationType() {
		return this.notificationType;
	}
}
