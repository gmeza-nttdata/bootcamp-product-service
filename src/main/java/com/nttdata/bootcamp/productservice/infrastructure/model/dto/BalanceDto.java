package com.nttdata.bootcamp.productservice.infrastructure.model.dto;

import com.nttdata.bootcamp.productservice.domain.entity.ProductType;
import com.nttdata.bootcamp.productservice.domain.entity.account.Account;
import com.nttdata.bootcamp.productservice.domain.entity.credit.Credit;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceDto {

    private String number;
    private ProductType productType;
    private String productSubtype;
    private BigDecimal balance;
    private BigDecimal credit;

    public static BalanceDto mapCreditToBalance(Credit credit){
        BalanceDto balance = new BalanceDto();
        balance.setNumber(credit.getNumber());
        balance.setProductSubtype(credit.getHasCard().toString());
        balance.setBalance(credit.getBalance());
        balance.setCredit(credit.getCreditLine());
        balance.setProductType(ProductType.CREDIT);
        return balance;
    }

    public static BalanceDto mapAccountToBalance(Account account) {
        BalanceDto balance = new BalanceDto();
        balance.setNumber(account.getNumber());
        balance.setProductSubtype(account.getType().toString());
        balance.setBalance(account.getBalance());
        balance.setCredit(null);
        balance.setProductType(ProductType.ACCOUNT);
        return balance;
    }

}