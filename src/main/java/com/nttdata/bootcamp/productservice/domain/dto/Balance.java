package com.nttdata.bootcamp.productservice.domain.dto;

import com.nttdata.bootcamp.productservice.domain.entity.Account;
import com.nttdata.bootcamp.productservice.domain.entity.Credit;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Balance {
    private String number;
    private String product;
    private BigDecimal balance;
    private BigDecimal credit;

    public static Balance mapCreditToBalance(Credit credit){
        Balance balance = new Balance();
        balance.setNumber(credit.getNumber());
        balance.setProduct("CREDIT");
        balance.setBalance(credit.getBalance());
        balance.setCredit(credit.getCreditLine());
        return balance;
    }

    public static Balance mapAccountToBalance(Account account) {
        Balance balance = new Balance();
        balance.setNumber(account.getNumber());
        balance.setProduct("ACCOUNT: " + account.getType().toString());
        balance.setBalance(account.getAmount());
        balance.setCredit(null);
        return balance;
    }

}
