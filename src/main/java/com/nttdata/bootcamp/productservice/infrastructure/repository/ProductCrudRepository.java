package com.nttdata.bootcamp.productservice.infrastructure.repository;

import com.nttdata.bootcamp.productservice.application.repository.ProductRepository;
import com.nttdata.bootcamp.productservice.domain.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductCrudRepository implements ProductRepository {

    private final IProductCrudRepository productCrudRepository;


    @Override
    public Flux<Product> getAll() {
        return null;
    }

    @Override
    public Mono<Product> getById() {
        return null;
    }

    @Override
    public Mono<Product> create() {
        return null;
    }

    @Override
    public Mono<Product> update() {
        return null;
    }

    @Override
    public Mono<Void> delete() {
        return null;
    }
}
