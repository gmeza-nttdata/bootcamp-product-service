package com.nttdata.bootcamp.productservice.infrastructure.rest;

import com.nttdata.bootcamp.productservice.application.ProductOperations;
import com.nttdata.bootcamp.productservice.domain.dto.BalanceDto;
import com.nttdata.bootcamp.productservice.domain.entity.Account;
import com.nttdata.bootcamp.productservice.domain.entity.Credit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductOperations operations;

    @PostMapping(value = "accounts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Account>> createAccount(@RequestBody Account account) {
        return operations.createAccount(account).map(ResponseEntity::ok);
    }

    @PostMapping(value = "credits", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Credit>> createCredit(@RequestBody Credit credit) {
        return operations.createCredit(credit).map(ResponseEntity::ok);
    }

    @GetMapping(value = "accounts/balance/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BalanceDto>> getAccountBalance(@PathVariable String number) {
        return operations.getAccountBalance(number).map(ResponseEntity::ok);
    }

    @GetMapping(value = "credits/balance/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<BalanceDto>> getCreditBalance(@PathVariable String number) {
        return operations.getCreditBalance(number).map(ResponseEntity::ok);
    }

}
