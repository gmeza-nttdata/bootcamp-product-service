package com.nttdata.bootcamp.project1.products.application.impl;

import com.nttdata.bootcamp.project1.products.application.product.ProductOperations;
import com.nttdata.bootcamp.project1.products.domain.entity.Account;
import com.nttdata.bootcamp.project1.products.domain.entity.Credit;
import com.nttdata.bootcamp.project1.products.infrastructure.service.AccountWebService;
import com.nttdata.bootcamp.project1.products.infrastructure.service.CreditWebService;
import com.nttdata.bootcamp.project1.products.infrastructure.service.UserWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
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

        return userWebService.get(account.getUserId())
                .flatMap(user -> accountWebService.getAll()
                        .filter(item -> item.getUserId().equals(user.getId()))
                        .collectList()
                        .flatMap(accounts -> accountWebService.create(Account.createAccount(account, user,accounts)))
                        .onErrorReturn(new Account())
                )
                .onErrorReturn(new Account());

    }

    @Override
    public Mono<Credit> createCredit(Credit credit) {
        return userWebService.get(credit.getUserId())
                .flatMap(user -> creditWebService.getAll()
                        .filter(item -> item.getUserId().equals(user.getId()))
                        .collectList()
                            .flatMap(credits -> creditWebService.create(Credit.createCredit(credit, user,credits)))
                            .onErrorReturn(new Credit())
                )
                .onErrorReturn(new Credit());
    }

}
