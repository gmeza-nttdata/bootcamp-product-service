package com.nttdata.bootcamp.project1.products.domain.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private Integer id;
    private Type type;
    private String fullName;
    private String address;
    private LocalDate birthDate;

}
