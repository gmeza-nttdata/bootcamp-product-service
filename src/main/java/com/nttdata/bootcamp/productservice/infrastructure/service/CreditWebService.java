package com.nttdata.bootcamp.productservice.infrastructure.service;

import com.nttdata.bootcamp.productservice.application.service.CreditService;
import com.nttdata.bootcamp.productservice.domain.entity.Credit;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CreditWebService implements CreditService {

    private static final String CREATE_CREDIT_SERVICE = "createCreditService";
    private static final String GET_ALL_CREDIT_SERVICE = "getAllCreditService";
    private final WebClient.Builder webClientBuilder;
    private final String URI;

    @Autowired
    public CreditWebService(WebClient.Builder webClientBuilder,
                            @Value("${bootcamp.web.credit:http://credit-service/users}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }

    @CircuitBreaker(name = GET_ALL_CREDIT_SERVICE, fallbackMethod = "getAllFallback")
    @Override
    public Flux<Credit> getAll() {
        return webClientBuilder.build().get().uri(URI)
                .retrieve().bodyToFlux(Credit.class);
    }

    @Override
    public Mono<Credit> get(String id) {
        return webClientBuilder.build().get()
                .uri(URI + "/{id}", id)
                .retrieve().bodyToMono(Credit.class);
    }

    @CircuitBreaker(name = CREATE_CREDIT_SERVICE, fallbackMethod = "createFallback")
    @Override
    public Mono<Credit> create(Credit credit) {
        return webClientBuilder.build().post().uri(URI)
                .body(Mono.justOrEmpty(credit), Credit.class)
                .retrieve().bodyToMono(Credit.class);
    }

    @Override
    public Mono<Credit> update(String id, Credit credit) {
        return webClientBuilder.build().put()
                .uri(URI + "/{id}", id)
                .body(Mono.justOrEmpty(credit), Credit.class)
                .retrieve().bodyToMono(Credit.class);
    }

    @Override
    public Mono<Void> delete(String id) {
        return webClientBuilder.build().delete()
                .uri(URI + "/{id}", id)
                .retrieve().bodyToMono(Void.class);
    }

    public Flux<Credit> getAllFallback(Exception e) {
        log.warn(e.toString());
        return Flux.empty();
    }

    public Mono<Credit> createFallback(Exception e) {
        log.warn(e.toString());
        return Mono.empty();
    }

}
