package com.nttdata.bootcamp.productservice.application.repository;

import com.nttdata.bootcamp.productservice.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Flux<Product> getAll();
    Mono<Product> getById();
    Mono<Product> create();
    Mono<Product> update();
    Mono<Void> delete();

}
