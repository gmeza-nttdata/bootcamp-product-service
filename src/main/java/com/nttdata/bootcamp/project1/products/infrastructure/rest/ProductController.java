package com.nttdata.bootcamp.project1.products.infrastructure.rest;

import com.nttdata.bootcamp.project1.products.application.ProductOperations;
import com.nttdata.bootcamp.project1.products.domain.entity.Account;
import com.nttdata.bootcamp.project1.products.domain.entity.Credit;
import com.nttdata.bootcamp.project1.products.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

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
