package com.nttdata.bootcamp.project1.products.infrastructure.rest;

import com.nttdata.bootcamp.project1.products.application.StatementOperations;
import com.nttdata.bootcamp.project1.products.domain.Statement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/statements")
@RequiredArgsConstructor
public class StatementController {

    private final StatementOperations statementOperations;

    @GetMapping
    public Flux<Statement> getStatements() {
        return statementOperations.queryAll();
    }

    @GetMapping("{id}")
    public Mono<Statement> get(@PathVariable String id) {
        return statementOperations.findById(id);
    }

    /*
    @PostMapping
    public Mono<Statement> post(@RequestBody Statement statement) {
        return statementOperations.create(statement);
    }

    @PutMapping("{id}")
    public Mono<Statement> put(@PathVariable String id, @RequestBody Statement statement) {
        return statementOperations.update(id,statement);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return statementOperations.delete(id);
    }

*/

}
