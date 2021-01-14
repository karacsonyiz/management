package com.example.jsp.Model;

import java.io.Serial;
import java.io.Serializable;

public class Response implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private boolean isValidRequest;
    private String message;

    public Response() {
    }

    public Response(boolean isValidRequest, String message) {
        this.isValidRequest = isValidRequest;
        this.message = message;
    }

    public boolean isValidRequest() {
        return isValidRequest;
    }

    public void setValidRequest(boolean validRequest) {
        isValidRequest = validRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "isValidRequest=" + isValidRequest +
                ", message='" + message + '\'' +
                '}';
    }
}
