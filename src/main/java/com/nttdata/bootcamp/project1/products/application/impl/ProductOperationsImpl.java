package com.nttdata.bootcamp.project1.products.application.impl;

import com.nttdata.bootcamp.project1.products.application.ProductOperations;
import com.nttdata.bootcamp.project1.products.domain.entity.Account;
import com.nttdata.bootcamp.project1.products.domain.entity.Credit;
import com.nttdata.bootcamp.project1.products.domain.entity.User;
import com.nttdata.bootcamp.project1.products.infrastructure.service.AccountWebService;
import com.nttdata.bootcamp.project1.products.infrastructure.service.CreditWebService;
import com.nttdata.bootcamp.project1.products.infrastructure.service.UserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOperationsImpl implements ProductOperations {

    @Autowired
    AccountWebService accountWebService;
    @Autowired
    CreditWebService creditWebService;
    @Autowired
    UserWebService userWebService;

    @Override
    public Mono<Account> createAccount(Account account) {

        User user = userWebService.get(account.getUserId()).block();

        List<Account> userAccounts = accountWebService.getAll()
                .collectList().block().stream()
                .filter(item -> item.getUserId().equals(account.getUserId()))
                .collect(Collectors.toList());

        Account newAccount =  Account.createAccount(account, user, userAccounts);
        if (newAccount!=null)
            return accountWebService.create(newAccount);
        return Mono.empty();
    }

    @Override
    public Mono<Credit> createCredit(Credit credit) {
        return null;
    }

}
