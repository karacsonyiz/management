package com.example.jsp.Model;

import java.io.Serial;
import java.io.Serializable;

public class Session implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Login login;
    private User user;
    private Response response;

    public Session() {
    }

    public Session(Login login, User user) {
        this.login = login;
        this.user = user;
    }

    public Session(Login login, User user, Response response) {
        this.login = login;
        this.user = user;
        this.response = response;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    @Override
    public String toString() {
        return "Session{" +
                "login=" + login +
                ", user=" + user +
                ", response=" + response +
                '}';
    }
}
