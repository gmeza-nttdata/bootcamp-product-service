package com.nttdata.bootcamp.productservice.infrastructure.repository;

import com.nttdata.bootcamp.productservice.infrastructure.model.dao.StatementDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IStatementCrudRepository extends ReactiveCrudRepository<StatementDao, String> {
}
