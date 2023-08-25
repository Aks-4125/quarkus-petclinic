package org.quarkus.samples.petclinic.owner;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.FormParam;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(name = "email")
    @NotEmpty
    @FormParam("email")
    public String email;

    @Column(name = "password")
    @NotEmpty
    @FormParam("password")
    public String password;
}
