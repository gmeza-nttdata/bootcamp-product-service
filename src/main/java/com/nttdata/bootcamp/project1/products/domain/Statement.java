package com.nttdata.bootcamp.project1.products.domain;

import com.nttdata.bootcamp.project1.products.application.product.AccountOperations;
import com.nttdata.bootcamp.project1.products.domain.dto.OperationType;
import com.nttdata.bootcamp.project1.products.domain.dto.ProductType;
import com.nttdata.bootcamp.project1.products.domain.entity.Account;
import com.nttdata.bootcamp.project1.products.domain.entity.Credit;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Statement {
    private String id;
    private ProductType productType;
    private String number;
    private OperationType operation;
    private BigDecimal amount;
    private LocalDateTime dateTime;

    public Statement(){}

    public Statement(Account account, OperationType operation, BigDecimal amount) {
        this.productType = ProductType.ACCOUNT;
        this.number = account.getNumber();
        this.dateTime = LocalDateTime.now();
        this.operation = operation;
        this.amount = amount;
    }

    public Statement(Credit credit, OperationType operation, BigDecimal amount) {
        this.productType = ProductType.CREDIT;
        this.number = credit.getNumber();
        this.dateTime = LocalDateTime.now();
        this.operation = operation;
        this.amount = amount;
    }

}
