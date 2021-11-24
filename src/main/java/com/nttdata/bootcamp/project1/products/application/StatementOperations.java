package com.nttdata.bootcamp.project1.products.application;

import com.nttdata.bootcamp.project1.products.domain.Statement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StatementOperations {

    Flux<Statement> queryAll();
    Mono<Statement> findById(String id);
    Mono<Statement> create(Statement statement);
    Mono<Statement> update(String id, Statement statement);
    Mono<Void> delete(String id);

}
