package com.nttdata.bootcamp.productservice.infrastructure.rest;

import com.nttdata.bootcamp.productservice.domain.dto.Balance;
import com.nttdata.bootcamp.productservice.domain.dto.OperationData;
import com.nttdata.bootcamp.productservice.application.product.AccountOperations;
import com.nttdata.bootcamp.productservice.domain.Statement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/operations/accounts")
@RequiredArgsConstructor
public class AccountOperationController {
    private final AccountOperations accountOperations;

    @PostMapping(value = "deposit")
    public Mono<Statement> deposit(@RequestBody OperationData operationData) {
        if (operationData.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Amount");

        return accountOperations.deposit(operationData);
    }

    @PostMapping(value = "withdraw")
    public Mono<Statement> withdraw(@RequestBody OperationData operationData) {
        if (operationData.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Amount");
        return accountOperations.withdraw(operationData);
    }

    @GetMapping(value = "balance/{number}")
    public Mono<Balance> getBalance(@PathVariable String number) {
        return accountOperations.getBalance(number);
    }

    @GetMapping(value = "statements/{number}")
    public Flux<Statement> getStatements(@PathVariable String number) {
        return accountOperations.getStatements(number);
    }

}
