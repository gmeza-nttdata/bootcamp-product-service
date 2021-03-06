package com.nttdata.bootcamp.productservice.application;

import com.nttdata.bootcamp.productservice.domain.entity.Product;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.AccountDto;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.BalanceDto;
import com.nttdata.bootcamp.productservice.domain.entity.credit.Credit;
import com.nttdata.bootcamp.productservice.domain.entity.account.Account;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.CreditDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/*
* Un cliente personal solo puede tener un máximo de una cuenta de ahorro, una cuenta corriente o cuentas a plazo fijo.
*
* Un cliente empresarial no puede tener una cuenta de ahorro o de plazo fijo pero sí múltiples cuentas corrientes.
*
* Las cuentas bancarias empresariales pueden tener uno o más titulares y cero o más firmantes autorizados.
*
* Un cliente puede tener un producto de crédito sin la obligación de tener una cuenta bancaria en la institución.
* */

public interface ProductOperations {

    Mono<Account> createAccount(AccountDto accountDto);
    Mono<Credit> createCredit(CreditDto creditDto);

    Mono<BalanceDto> getCreditBalance(String number);
    Mono<BalanceDto> getAccountBalance(String number);

    Flux<Product> getAvailableProducts(Integer userId);

}
