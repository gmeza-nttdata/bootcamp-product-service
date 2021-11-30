package com.nttdata.bootcamp.productservice.infrastructure.service;

import com.nttdata.bootcamp.productservice.application.service.AccountService;
import com.nttdata.bootcamp.productservice.domain.entity.Account;
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
public class AccountWebService implements AccountService {

    private static final String GET_ALL_ACCOUNT_SERVICE = "getAllAccountService";
    private static final String CREATE_ACCOUNT_SERVICE = "createAccountService";
    private final WebClient.Builder webClientBuilder;
    private final String URI;

    @Autowired
    public AccountWebService(WebClient.Builder webClientBuilder,
                             @Value("${bootcamp.web.account:http://account-service/users}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }

    @CircuitBreaker(name = GET_ALL_ACCOUNT_SERVICE, fallbackMethod = "getAllFallback")
    @Override
    public Flux<Account> getAll() {
        return webClientBuilder.build().get().uri(URI)
                .retrieve().bodyToFlux(Account.class);
    }

    @Override
    public Mono<Account> get(String id) {
        return webClientBuilder.build().get()
                .uri(URI + "/{id}", id)
                .retrieve().bodyToMono(Account.class);
    }

    @CircuitBreaker(name = CREATE_ACCOUNT_SERVICE, fallbackMethod = "createFallback")
    @Override
    public Mono<Account> create(Account account) {
        return webClientBuilder.build().post().uri(URI)
                .body(Mono.justOrEmpty(account), Account.class)
                .retrieve().bodyToMono(Account.class);
    }

    @Override
    public Mono<Account> update(String id, Account account) {
        return webClientBuilder.build().put()
                .uri(URI + "/{id}", id)
                .body(Mono.justOrEmpty(account), Account.class)
                .retrieve().bodyToMono(Account.class);
    }


    @Override
    public Mono<Void> delete(String id) {
        return webClientBuilder.build().delete()
                .uri(URI + "/{id}", id)
                .retrieve().bodyToMono(Void.class);
    }

    Flux<Account> getAllFallback(Exception e) {
        log.warn(e.toString());
        return Flux.empty();
    }

    Mono<Account> createFallback(Exception e) {
        log.warn(e.toString());
        return Mono.empty();
    }

}
