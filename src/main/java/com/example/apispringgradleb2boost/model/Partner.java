package com.example.apispringgradleb2boost.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Locale;


@Data
@Entity
@Table(name = "partners")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "ref", unique = true)
    private String reference;

    private Locale locale;

    @Column(name = "expires")
    private String expirationTime;


}
