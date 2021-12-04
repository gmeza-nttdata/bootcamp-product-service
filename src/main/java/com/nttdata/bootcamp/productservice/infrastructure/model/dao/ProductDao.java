package com.nttdata.bootcamp.productservice.infrastructure.model.dao;

import com.nttdata.bootcamp.productservice.domain.entity.ProductType;
import com.nttdata.bootcamp.productservice.domain.entity.account.AccountContract;
import com.nttdata.bootcamp.productservice.domain.entity.credit.CreditContract;
import com.nttdata.bootcamp.productservice.domain.entity.user.UserType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class ProductDao {
    @Id
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

}
