package com.nttdata.bootcamp.project1.products.application.impl;

import com.nttdata.bootcamp.project1.products.application.product.AccountOperations;
import com.nttdata.bootcamp.project1.products.application.repository.StatementRepository;
import com.nttdata.bootcamp.project1.products.domain.Statement;
import com.nttdata.bootcamp.project1.products.domain.dto.Balance;
import com.nttdata.bootcamp.project1.products.domain.dto.OperationData;
import com.nttdata.bootcamp.project1.products.domain.dto.OperationType;
import com.nttdata.bootcamp.project1.products.domain.dto.ProductType;
import com.nttdata.bootcamp.project1.products.infrastructure.service.AccountWebService;
import com.nttdata.bootcamp.project1.products.infrastructure.service.UserWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AccountOperationsImpl implements AccountOperations {

    private final StatementRepository statementRepository;

    @Autowired
    AccountWebService accountWebService;
    @Autowired
    UserWebService userWebService;

    @Override
    public Mono<Statement> deposit(OperationData operationData) {

        return accountWebService.get(operationData.getNumber())
                .flatMap(account -> {
                    // Deposit Operation
                    account.setAmount(account.getAmount().add(operationData.getAmount()));
                    return accountWebService.update(account.getNumber(), account)
                            .flatMap(
                                    updatedAccount ->
                                            statementRepository
                                                    .create(new Statement(updatedAccount, OperationType.DEPOSIT, operationData.getAmount()))
                            )
                            .onErrorReturn(new Statement());
                })
                .onErrorReturn(new Statement());
    }

    @Override
    public Mono<Statement> withdraw(OperationData operationData) {
        return accountWebService.get(operationData.getNumber())
                .flatMap(account -> {
                    // Withdraw Operation

                    if (account.getAmount().compareTo(operationData.getAmount()) < 0)
                        return null;

                    account.setAmount(account.getAmount().subtract(operationData.getAmount()));
                    return accountWebService.update(account.getNumber(), account)
                            .flatMap(
                                    updatedAccount ->
                                            statementRepository
                                                    .create(new Statement(account, OperationType.WITHDRAWAL, operationData.getAmount()))
                            )
                            .onErrorReturn(new Statement());
                })
                .onErrorReturn(new Statement());
    }

    @Override
    public Mono<Balance> getBalance(String accountNumber) {
        return accountWebService.get(accountNumber)
                .map(Balance::mapAccountToBalance);
    }

    @Override
    public Flux<Statement> getStatements(String accountNumber) {
        return statementRepository.queryAll()
                .filter(statement ->
                        statement.getProductType().equals(ProductType.ACCOUNT) && statement.getNumber().equals(accountNumber));
    }
}
