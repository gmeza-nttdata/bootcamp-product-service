package com.nttdata.bootcamp.productservice.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationData {
    private String number;
    private BigDecimal amount;
}
