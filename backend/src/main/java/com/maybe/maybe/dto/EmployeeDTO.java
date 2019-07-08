package com.maybe.maybe.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = 4, max = 25)
    private String password;

    @NotNull
    @Size(min = 1, max = 2)
    private Integer roleId;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String name, String login, String password, Integer roleId) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
