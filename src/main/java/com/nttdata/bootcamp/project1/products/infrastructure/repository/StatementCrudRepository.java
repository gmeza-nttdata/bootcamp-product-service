package com.nttdata.bootcamp.project1.products.infrastructure.repository;

import com.nttdata.bootcamp.project1.products.application.repository.StatementRepository;
import com.nttdata.bootcamp.project1.products.domain.Statement;
import com.nttdata.bootcamp.project1.products.domain.entity.Account;
import com.nttdata.bootcamp.project1.products.infrastructure.model.dao.StatementDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class StatementCrudRepository implements StatementRepository {

    @Autowired
    IStatementCrudRepository repository;


    @Override
    public Flux<Statement> queryAll() {
        return repository.findAll()
                .map(this::mapStatementDaoToStatement);
    }

    @Override
    public Mono<Statement> findById(String id) {
        return repository.findById(id)
                .map(this::mapStatementDaoToStatement);
    }

    @Override
    public Mono<Statement> create(Statement statement) {
        return repository.save(mapStatementToStatementDao(statement))
                .map(this::mapStatementDaoToStatement);
    }

    @Override
    public Mono<Statement> update(String id, Statement statement) {
        return repository.findById(id)
                .flatMap(s -> {
                    s.setId(id);
                    return Mono.just(mapStatementToStatementDao(statement));
                }).flatMap(repository::save)
                .map(this::mapStatementDaoToStatement);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    private Statement mapStatementDaoToStatement(StatementDao statementDao) {
        Statement statement = new Statement();
        BeanUtils.copyProperties(statementDao, statement);
        return statement;
    }

    private StatementDao mapStatementToStatementDao(Statement statement) {
        StatementDao statementDao = new StatementDao();
        BeanUtils.copyProperties(statement, statementDao);
        return statementDao;
    }


}
