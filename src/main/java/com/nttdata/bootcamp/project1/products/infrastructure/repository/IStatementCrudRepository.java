package com.nttdata.bootcamp.project1.products.infrastructure.repository;

import com.nttdata.bootcamp.project1.products.infrastructure.model.dao.StatementDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IStatementCrudRepository extends ReactiveCrudRepository<StatementDao, String> {
}
