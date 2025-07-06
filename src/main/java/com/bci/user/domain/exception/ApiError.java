package com.bci.user.domain.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Generated;

import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(
        fieldVisibility = Visibility.ANY
)
public class ApiError {
    private Integer status;
    private String code;
    private LocalDateTime timestamp;
    private String message;
    private Type type;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(Integer status, String message, Type type) {
        this();
        this.status = status;
        this.message = message;
        this.type = type;
    }

    public ApiError(Integer status, String message, Type type, String code) {
        this();
        this.status = status;
        this.message = message;
        this.type = type;
        this.code = code;
    }

    @Generated
    public Integer getStatus() {
        return this.status;
    }

    @Generated
    public String getCode() {
        return this.code;
    }

    @Generated
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }

    @Generated
    public Type getType() {
        return this.type;
    }

    @Generated
    public void setStatus(Integer status) {
        this.status = status;
    }

    @Generated
    public void setCode(String code) {
        this.code = code;
    }

    @Generated
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Generated
    public void setMessage(String message) {
        this.message = message;
    }

    @Generated
    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type {
        BUSINESS,
        TECHNICAL;

        private Type() {
        }
    }
}
