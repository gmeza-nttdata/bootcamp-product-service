package com.nttdata.bootcamp.project1.products.infrastructure.rest;

import com.nttdata.bootcamp.project1.products.domain.entity.Statements;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationsController {

    @PostMapping(value = "deposit")
    public Mono<Statements> deposit() {
        return null;
    }

    @PostMapping(value = "withdraw")
    public Mono<Statements> withdraw() {
        return null;
    }

}
