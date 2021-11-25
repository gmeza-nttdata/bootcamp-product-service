package com.nttdata.bootcamp.productservice.application.impl;

import com.nttdata.bootcamp.productservice.application.StatementOperations;
import com.nttdata.bootcamp.productservice.application.repository.StatementRepository;
import com.nttdata.bootcamp.productservice.domain.Statement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StatementOperationsImpl implements StatementOperations {

    private final StatementRepository statementRepository;

    @Override
    public Flux<Statement> queryAll() {
        return statementRepository.queryAll();
    }

    @Override
    public Mono<Statement> findById(String id) {
        return statementRepository.findById(id);
    }

    @Override
    public Mono<Statement> create(Statement statement) {
        return statementRepository.create(statement);
    }

    @Override
    public Mono<Statement> update(String id, Statement statement) {
        return statementRepository.update(id, statement);
    }

    @Override
    public Mono<Void> delete(String id) {
        return statementRepository.delete(id);
    }
}
