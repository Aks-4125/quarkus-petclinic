package org.quarkus.samples.petclinic.login;

import javax.ws.rs.FormParam;

public class LoginForm {

    @FormParam("email")
    public String email;

    @FormParam("password")
    public String password;

    @Override
    public String toString() {
        return "LoginForm{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
