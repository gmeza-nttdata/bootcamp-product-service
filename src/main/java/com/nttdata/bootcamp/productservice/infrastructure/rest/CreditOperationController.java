package com.nttdata.bootcamp.productservice.infrastructure.rest;

import com.nttdata.bootcamp.productservice.domain.dto.Balance;
import com.nttdata.bootcamp.productservice.domain.dto.OperationData;
import com.nttdata.bootcamp.productservice.application.product.CreditOperations;
import com.nttdata.bootcamp.productservice.domain.Statement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/operations/credits")
@RequiredArgsConstructor
public class CreditOperationController {
    private final CreditOperations creditOperations;

    @PostMapping("pay")
    Mono<Statement> payCredit(@RequestBody OperationData operationData) {
        if (operationData.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Amount");
        return creditOperations.payCredit(operationData);
    }

    @PostMapping("consume")
    Mono<Statement> consumeCredit(@RequestBody OperationData operationData){
        if (operationData.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Amount");
        return creditOperations.consumeCredit(operationData);
    }

    @GetMapping("balance/{number}")
    Mono<Balance> getBalance(@PathVariable String number) {
        return creditOperations.getBalance(number);
    }

    @GetMapping("statements/{number}")
    Flux<Statement> getStatements(@PathVariable String number) {
        return creditOperations.getStatements(number);
    }

}
