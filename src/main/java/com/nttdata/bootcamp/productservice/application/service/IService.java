package com.nttdata.bootcamp.productservice.application.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IService<T,Id> {

    Flux<T> getAll();
    Mono<T> get(Id id);
    Mono<T> create(T t);
    Mono<T> update(Id id, T t);
    Mono<Void> delete(Id id);

}
