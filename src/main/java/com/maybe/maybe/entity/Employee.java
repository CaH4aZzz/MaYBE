package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Employee extends AbstractEntity {
    @NotNull
    @Column(name = "user_name")
    private String userName;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    @Column(name = "role")
    private UserRole userRole;


    public Employee() {
    }

    public Employee(String userName, String login, String password, UserRole userRole) {
        this.userName = userName;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
