package com.maybe.maybe.entity;

import com.maybe.maybe.entity.enums.UserRole;
import com.maybe.maybe.entity.enums.converter.UserRoleConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee extends AbstractNameEntity {
    @NotNull
    @Column(name = "login", nullable = false, length = 50)
    private String login;

    @NotNull
    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @NotNull
    @Convert(converter = UserRoleConverter.class)
    @Column(name = "role_id", nullable = false)
    private UserRole userRole;

    @OneToMany(mappedBy = "employee")
    private Set<Invoice> invoiceList;

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

    public Set<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(Set<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }
}
