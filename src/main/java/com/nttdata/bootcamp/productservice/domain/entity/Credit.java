package com.nttdata.bootcamp.productservice.domain.entity;

import com.nttdata.bootcamp.productservice.domain.dto.CreditType;
import com.nttdata.bootcamp.productservice.domain.dto.Type;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Data
public class Credit {
    private static final int PERSONAL_MAX = 1;
    private static final int BUSINESS_MAX = Integer.MAX_VALUE;

    private String number;
    private Integer userId;
    private Type userType;
    private CreditType creditType;
    private String currencyName;
    private BigDecimal balance;

    private BigDecimal creditLine;
    private LocalDate expiration;


    public static Credit createCredit(Credit credit, User user, List<Credit> userCredits) {
        Credit newCredit = null;
        credit.setNumber(null);

        // Return null if user doesn't exist
        credit.userType = user.getType();
        if (credit.userType == null)
            return null;

        if (user.getType().equals(Type.BUSINESS)) {
            credit.userId = user.getId();
            credit.userType = Type.BUSINESS;
            credit.balance = BigDecimal.ZERO;
            newCredit = credit;
        } else if (user.getType().equals(Type.PERSONAL)){
            if (userCredits.size() >= PERSONAL_MAX)
                return null;
            credit.userId = user.getId();
            credit.userType = Type.PERSONAL;
            credit.balance = BigDecimal.ZERO;
            newCredit = credit;
        }

        // DEFAULTS:

        if (credit.creditType == null)
            credit.creditType = CreditType.SIMPLE;

        if (credit.currencyName == null)
            credit.currencyName = "PEN";

        if (credit.creditLine == null)
            credit.creditLine = BigDecimal.valueOf(5000);

        //if (credit.expiration == null)
        //    credit.expiration = LocalDate.now().plusDays(30);

        return newCredit;
    }

}
