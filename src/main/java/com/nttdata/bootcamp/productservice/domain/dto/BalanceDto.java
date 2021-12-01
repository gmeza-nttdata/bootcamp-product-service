package com.nttdata.bootcamp.productservice.domain.dto;

import com.nttdata.bootcamp.productservice.domain.entity.Account;
import com.nttdata.bootcamp.productservice.domain.entity.Credit;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDto {

    enum Product {
        CREDIT, ACCOUNT
    }

    private String number;
    private Product product;
    private String productType;
    private BigDecimal balance;
    private BigDecimal credit;

    public static BalanceDto mapCreditToBalance(Credit credit){
        BalanceDto balance = new BalanceDto();
        balance.setNumber(credit.getNumber());
        balance.setProductType(credit.getCreditType().toString());
        balance.setBalance(credit.getBalance());
        balance.setCredit(credit.getCreditLine());
        balance.setProduct(Product.CREDIT);
        return balance;
    }

    public static BalanceDto mapAccountToBalance(Account account) {
        BalanceDto balance = new BalanceDto();
        balance.setNumber(account.getNumber());
        balance.setProductType(account.getType().toString());
        balance.setBalance(account.getBalance());
        balance.setCredit(null);
        balance.setProduct(Product.ACCOUNT);
        return balance;
    }

}