package com.nttdata.bootcamp.project1.products.application.impl;

import com.nttdata.bootcamp.project1.products.application.product.CreditOperations;
import com.nttdata.bootcamp.project1.products.application.repository.StatementRepository;
import com.nttdata.bootcamp.project1.products.domain.Statement;
import com.nttdata.bootcamp.project1.products.domain.dto.Balance;
import com.nttdata.bootcamp.project1.products.domain.dto.OperationData;
import com.nttdata.bootcamp.project1.products.domain.dto.OperationType;
import com.nttdata.bootcamp.project1.products.domain.dto.ProductType;
import com.nttdata.bootcamp.project1.products.infrastructure.service.CreditWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreditOperationsImpl implements CreditOperations {
    private final StatementRepository statementRepository;
    @Autowired
    CreditWebService creditWebService;


    @Override
    public Mono<Statement> payCredit(OperationData operationData) {
        return creditWebService.get(operationData.getNumber())
                .flatMap(credit -> {
                    // Payment Operation
                    credit.setBalance(credit.getBalance().subtract(operationData.getAmount()));
                    return creditWebService.update(credit.getNumber(), credit)
                            .flatMap(
                                    updatedCredit ->
                                            statementRepository
                                                    .create(new Statement(updatedCredit, OperationType.PAYMENT, operationData.getAmount()))
                            )
                            .onErrorReturn(new Statement());
                })
                .onErrorReturn(new Statement());
    }

    @Override
    public Mono<Statement> consumeCredit(OperationData operationData) {
        return creditWebService.get(operationData.getNumber())
                .flatMap(credit -> {
                    if (credit.getBalance().add(operationData.getAmount()).compareTo(credit.getCreditLine()) > 0)
                        return null;
                    // Consumption Operation
                    credit.setBalance(credit.getBalance().add(operationData.getAmount()));
                    return creditWebService.update(credit.getNumber(), credit)
                            .flatMap(
                                    updatedCredit ->
                                            statementRepository
                                                    .create(new Statement(updatedCredit, OperationType.CONSUMPTION, operationData.getAmount()))
                            )
                            .onErrorReturn(new Statement());
                })
                .onErrorReturn(new Statement());
    }

    @Override
    public Mono<Balance> getBalance(String number) {
        return creditWebService.get(number)
                .map(Balance::mapCreditToBalance);
    }

    @Override
    public Flux<Statement> getStatements(String number) {
        return statementRepository.queryAll()
                .filter(statement ->
                        statement.getProductType().equals(ProductType.CREDIT) && statement.getNumber().equals(number));
    }
}
