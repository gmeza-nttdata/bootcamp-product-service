package com.nttdata.bootcamp.productservice.domain.entity;

import com.nttdata.bootcamp.productservice.domain.entity.account.Account;
import com.nttdata.bootcamp.productservice.domain.entity.account.AccountContract;
import com.nttdata.bootcamp.productservice.domain.entity.credit.Credit;
import com.nttdata.bootcamp.productservice.domain.entity.credit.CreditContract;
import com.nttdata.bootcamp.productservice.domain.entity.user.UserType;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.AccountDto;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.CreditDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {
    private String id;
    /** Main attributes: Ids. */
    private ProductType productType;
    private UserType userType;
    private String productSubtype;

    /** Initial attributes: */
    private BigDecimal initialBalance;
    private BigDecimal initialCreditLine;
    private String currencyName;
    private Boolean hasCreditCard;

    /** Credit card condition. */
    private Boolean requiresCreditCardUponCreation;

    /** Limit per userType. */
    private Integer maxForUser;

    /** Contract attributes: */
    private CreditContract creditContract;
    private AccountContract accountContract;

    public Boolean wrongInitialBalance(AccountDto accountDto) {

        if (accountDto.getBalance()==null)
            accountDto.setBalance(BigDecimal.ZERO);

        return this.getAccountContract().getHasMinimumInitialBalance() &&
                (accountDto.getBalance().compareTo
                        (this.getAccountContract().getMinimumInitialBalance()) < 0);
    }

    public static Account createAccount(AccountDto accountDto,
                                        Product product) {
        Account account = Account.builder().build();
        account.setNumber(null);
        account.setUserId(accountDto.getUserId());
        account.setType(accountDto.getType());
        account.setCurrencyName(accountDto.getCurrencyName());
        account.setBalance(accountDto.getBalance());
        account.setContract(product.getAccountContract());
        account.setHolders(accountDto.getHolders());
        account.setSigners(accountDto.getSigners());
        System.out.println(account.getContract().toString());
        return account;
    }

    public static Credit createCredit(CreditDto creditDto, Product product) {

        Credit  credit = Credit.builder().build();
        credit.setNumber(null);
        credit.setUserId(creditDto.getUserId());
        credit.setHasCard(creditDto.getHasCard());
        credit.setCurrencyName(creditDto.getCurrencyName());
        credit.setBalance(BigDecimal.ZERO);
        credit.setCreditLine(creditDto.getCreditLine());
        credit.setRate(creditDto.getRate());
        credit.setCutoffDate(creditDto.getCutoffDate());
        credit.setPaymentDate(creditDto.getPaymentDate());

        return credit;
    }
}
