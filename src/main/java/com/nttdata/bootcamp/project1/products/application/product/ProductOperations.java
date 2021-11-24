package com.nttdata.bootcamp.project1.products.application.product;

import com.nttdata.bootcamp.project1.products.domain.entity.Account;
import com.nttdata.bootcamp.project1.products.domain.entity.Credit;
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

    Mono<Account> createAccount(Account account);
    Mono<Credit> createCredit(Credit credit);

}
