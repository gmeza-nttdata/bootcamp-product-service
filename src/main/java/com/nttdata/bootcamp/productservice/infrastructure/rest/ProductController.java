package com.nttdata.bootcamp.productservice.infrastructure.rest;

import com.nttdata.bootcamp.productservice.application.product.ProductOperations;
import com.nttdata.bootcamp.productservice.domain.entity.Account;
import com.nttdata.bootcamp.productservice.domain.entity.Credit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductOperations productOperations;

    @PostMapping(value = "accounts")
    public Mono<Account> createAccount(@RequestBody Account account) {
        return productOperations.createAccount(account);
    }

    @PostMapping(value = "credits")
    public Mono<Credit> createCredit(@RequestBody Credit credit) {
        return productOperations.createCredit(credit);
    }


}
