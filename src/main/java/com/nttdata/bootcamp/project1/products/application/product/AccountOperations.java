package com.nttdata.bootcamp.project1.products.application.product;


/*
* Un cliente puede hacer depósitos y retiros de sus cuentas bancarias.
*
* El sistema debe permitir consultar los saldos disponibles en sus cuentas bancarias
*
* El sistema debe permitir consultar todos los movimientos de un producto bancario que tiene un cliente.
* */

import com.nttdata.bootcamp.project1.products.domain.dto.Balance;
import com.nttdata.bootcamp.project1.products.domain.dto.OperationData;
import com.nttdata.bootcamp.project1.products.domain.entity.Account;
import com.nttdata.bootcamp.project1.products.domain.Statement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountOperations {

    Mono<Statement> deposit(OperationData operationData);
    Mono<Statement> withdraw(OperationData operationData);

    Mono<Balance> getBalance(String accountNumber);

    Flux<Statement> getStatements(String accountNumber);

}
