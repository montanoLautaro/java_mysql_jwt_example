package com.example.crud_mysql_jwt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String resourceName;
    private String rowName;
    private long rowValue;

    public ResourceNotFoundException(String resourceName, String rowName, long rowValue) {
        super(String.format("%s no se encontró por:  %s = '%s'", resourceName, rowName, rowValue));
        this.resourceName = resourceName;
        this.rowName = rowName;
        this.rowValue = rowValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public long getRowValue() {
        return rowValue;
    }

    public void setRowValue(long rowValue) {
        this.rowValue = rowValue;
    }
}
