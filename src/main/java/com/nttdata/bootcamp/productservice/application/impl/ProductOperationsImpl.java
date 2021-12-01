package com.nttdata.bootcamp.productservice.application.impl;

import com.nttdata.bootcamp.productservice.application.ProductOperations;
import com.nttdata.bootcamp.productservice.domain.dto.BalanceDto;
import com.nttdata.bootcamp.productservice.domain.entity.Credit;
import com.nttdata.bootcamp.productservice.infrastructure.service.AccountWebService;
import com.nttdata.bootcamp.productservice.infrastructure.service.CreditWebService;
import com.nttdata.bootcamp.productservice.infrastructure.service.UserWebService;
import com.nttdata.bootcamp.productservice.domain.entity.Account;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class ProductOperationsImpl implements ProductOperations {

    private final AccountWebService accountWebService;
    private final CreditWebService creditWebService;
    private final UserWebService userWebService;

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

    @Override
    public Mono<BalanceDto> getCreditBalance(String number) {
        return creditWebService.get(number).map(BalanceDto::mapCreditToBalance);
    }

    @Override
    public Mono<BalanceDto> getAccountBalance(String number) {
        return accountWebService.get(number).map(BalanceDto::mapAccountToBalance);
    }

}
