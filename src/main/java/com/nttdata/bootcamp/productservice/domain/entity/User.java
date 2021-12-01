package com.nttdata.bootcamp.productservice.domain.entity;

import com.nttdata.bootcamp.productservice.domain.dto.UserType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private Integer id;
    private UserType type;
    private String fullName;
    private String address;
    private LocalDate birthDate;

}
