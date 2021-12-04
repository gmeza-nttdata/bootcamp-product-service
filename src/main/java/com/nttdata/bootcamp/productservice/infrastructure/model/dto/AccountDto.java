package com.nttdata.bootcamp.productservice.infrastructure.model.dto;

import com.nttdata.bootcamp.productservice.domain.entity.account.AccountContract;
import com.nttdata.bootcamp.productservice.domain.entity.account.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class AccountDto {
    /** main holder. */
    private Integer userId;
    /** account type. */
    private AccountType type;
    /** Currency of the account. */
    private String currencyName;
    /** Current Account balance. */
    private BigDecimal balance =  BigDecimal.ZERO;

    // Only for Business:
    /** Holders list. */
    private List<Integer> holders;
    /** Singers list. */
    private List<Integer> signers;
}
