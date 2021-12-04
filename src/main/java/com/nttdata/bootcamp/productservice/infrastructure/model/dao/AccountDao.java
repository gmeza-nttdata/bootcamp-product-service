package com.nttdata.bootcamp.productservice.infrastructure.model.dao;

import com.nttdata.bootcamp.productservice.domain.entity.account.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AccountDao {
    /** account number or id. */
    private String number;
    /** main holder. */
    private Integer userId;
    /** account type. */
    private AccountType type;
    /** Currency of the account. */
    private String currencyName;
    /** Current Account balance. */
    private BigDecimal balance;
    /** Account Contract. */
    private AccountContractDao contract;

    // Only for Business:
    /** Holders list. */
    private List<Integer> holders;
    /** Singers list. */
    private List<Integer> signers;

}
