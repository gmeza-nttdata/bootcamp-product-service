package com.nttdata.bootcamp.project1.products.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
account.personal.n.current.max=1
account.personal.n.savings.max=1
account.personal.n.fixed-term.max=1

account.business.n.current.max=2147483647
account.business.n.savings.max=0
account.business.n.fixed-term.max=0

account.personal.n.holder.max=1
account.personal.n.holder.min=1
account.business.n.holder.max=2147483647
account.business.n.holder.min=1

account.personal.n.signer.max=1
account.personal.n.signer.min=1
account.business.n.signer.max=2147483647
account.business.n.signer.min=0
*/

@Data
public class Account {

    private static final int PERSONAL_CURRENT_MAX = 1;
    private static final int PERSONAL_SAVINGS_MAX = 1;
    private static final int PERSONAL_FIXED_TERM_MAX = 1;

    private static final int BUSINESS_CURRENT_MAX = Integer.MAX_VALUE;
    private static final int BUSINESS_SAVINGS_MAX = 0;
    private static final int BUSINESS_FIXED_TERM_MAX = 0;

    private static final int PERSONAL_HOLDER_MAX = 1;
    private static final int PERSONAL_HOLDER_MIN = 1;

    private static final int BUSINESS_HOLDER_MAX = Integer.MAX_VALUE;
    private static final int BUSINESS_HOLDER_MIN = 1;

    private static final int PERSONAL_SIGNER_MAX = 1;
    private static final int PERSONAL_SIGNER_MIN = 1;

    private static final int BUSINESS_SIGNER_MAX = Integer.MAX_VALUE;
    private static final int BUSINESS_SIGNER_MIN = 0;

    private String number;
    private Integer userId;
    private AccountType type;
    private String currencyName;
    private BigDecimal amount;

    // Only for Business:
    private List<Integer> holders;
    private List<Integer> signers;


    public static Account createAccount(Account account, User user, List<Account> userAccounts) {
        // TODO: Logic
        account.setNumber(null);
        if (account.holders==null || account.holders.isEmpty()) {
            account.holders = new ArrayList<>(); account.holders.add(user.getId());
        } else if (!account.holders.contains(user.getId())) account.holders.add(user.getId());

        if (account.signers==null) account.signers = new ArrayList<>();

        Account newAccount = null;

        if (!account.verify() || user.getType()==null) return null;

        List<Account> accountListByType = userAccounts.stream()
                .filter(item -> item.type == account.type)
                .collect(Collectors.toList());

        switch (account.type) {
            case CURRENT:
                if (user.getType().equals(Type.BUSINESS) && (accountListByType.size() < BUSINESS_CURRENT_MAX) && account.verifyBusinessCurrentAccount()) {
                    newAccount =  account;
                } else if (user.getType().equals(Type.PERSONAL) && (accountListByType.size() < PERSONAL_CURRENT_MAX)) {
                    newAccount = account;
                }
                break;
            case SAVINGS:
                if (user.getType().equals(Type.BUSINESS) && (accountListByType.size() < BUSINESS_SAVINGS_MAX)) {
                    // Empty
                } else if (user.getType().equals(Type.PERSONAL) && (accountListByType.size() < PERSONAL_SAVINGS_MAX)) {
                    newAccount = account;
                }
                break;
            case FIXED_TERM:
                if (user.getType().equals(Type.BUSINESS) && (accountListByType.size() < BUSINESS_FIXED_TERM_MAX)) {
                    // Empty
                } else if (user.getType().equals(Type.PERSONAL) && (accountListByType.size() < PERSONAL_FIXED_TERM_MAX)) {
                    newAccount = account;
                }
                break;
        }

        return newAccount;
    }

    private boolean verifyBusinessCurrentAccount() {
        return ( this.holders!=null && (this.holders.size() >= BUSINESS_HOLDER_MIN) && (this.holders.size() <= BUSINESS_HOLDER_MAX) ) &&
                (this.signers.size() >= BUSINESS_SIGNER_MIN) && (this.signers.size() <= BUSINESS_SIGNER_MAX);
    }

    private boolean verify() {
        return !(this.userId == null || this.type == null);
    }

}
