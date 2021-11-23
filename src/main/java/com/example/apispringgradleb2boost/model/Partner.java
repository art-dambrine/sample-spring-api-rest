package com.example.apispringgradleb2boost.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "partners")
public class Partner {
    @Id
    private Long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "ref")
    private String reference;

    private String locale;

    @Column(name = "expires")
    private String expirationTime;
}
