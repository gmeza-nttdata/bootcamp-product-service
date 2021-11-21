package com.nttdata.bootcamp.project1.products.infrastructure.model.dao;

import com.nttdata.bootcamp.project1.products.domain.entity.Type;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDao {

    private Integer id;
    private Type type;
    private String fullName;
    private String address;
    private LocalDate birthDate;

}