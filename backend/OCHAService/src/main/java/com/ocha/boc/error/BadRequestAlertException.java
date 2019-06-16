package com.ocha.boc.error;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class BadRequestAlertException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String statusCode;

    public BadRequestAlertException(String defaultMessage, String entityName, String statusCode) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, statusCode);
    }

    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String statusCode) {
        super(type, defaultMessage, Status.BAD_REQUEST, null, null, null, getAlertParameters(entityName, statusCode));
        this.entityName = entityName;
        this.statusCode = statusCode;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return statusCode;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String statusCode) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", "error." + statusCode);
        parameters.put("params", entityName);
        return parameters;
    }
}
