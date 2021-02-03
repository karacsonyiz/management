package com.example.jsp.Model;

import com.example.jsp.GeneratedEntity.GeneratedUserEntity;

import java.io.Serial;
import java.io.Serializable;

public class Session implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Login login;
    private GeneratedUserEntity user;
    private Response response;
    private String actionMessage;
    private String actionResponse;

    public Session() {
    }

    public Session(Login login, GeneratedUserEntity user) {
        this.login = login;
        this.user = user;
    }

    public Session(Login login, GeneratedUserEntity user, String actionMessage) {
        this.login = login;
        this.user = user;
        this.actionMessage = actionMessage;
    }

    public Session(Login login) {
        this.login = login;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public GeneratedUserEntity getUser() {
        return user;
    }

    public void setUser(GeneratedUserEntity user) {
        this.user = user;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getActionMessage() {
        return actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public String getActionResponse() {
        return actionResponse;
    }

    public void setActionResponse(String actionResponse) {
        this.actionResponse = actionResponse;
    }

    public Session(Login login, GeneratedUserEntity user, Response response, String actionMessage) {
        this.login = login;
        this.user = user;
        this.response = response;
        this.actionMessage = actionMessage;
    }
}
