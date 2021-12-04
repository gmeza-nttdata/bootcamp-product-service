package com.nttdata.bootcamp.productservice.infrastructure.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreditDto {
    private String number;
    private Integer userId;
    private Boolean hasCard;
    private String currencyName;
    private BigDecimal balance = BigDecimal.ZERO;

    private BigDecimal creditLine;
    private BigDecimal rate;
    private Integer cutoffDate;
    private Integer paymentDate;
}
