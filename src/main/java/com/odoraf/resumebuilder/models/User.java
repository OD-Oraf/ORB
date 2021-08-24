package com.odoraf.resumebuilder.models;

import javax.persistence.*;

//Create table called user in database
@Entity
@Table(name = "Users")
public class User {
    //Primary key

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private int id;
    private String userName;
    private String password;
    //User needs to be active at the point of login
    private boolean active;
    //User vs admin role
    private String roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
