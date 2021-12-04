package com.nttdata.bootcamp.productservice.infrastructure.repository;

import com.nttdata.bootcamp.productservice.infrastructure.model.dao.ProductDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IProductCrudRepository extends ReactiveCrudRepository<ProductDao, String> {
}
