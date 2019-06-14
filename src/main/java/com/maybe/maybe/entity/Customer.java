package com.maybe.maybe.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "customer")
public class Customer extends AbstractNameEntity {
}
