package com.nttdata.bootcamp.productservice.infrastructure.service;

import com.nttdata.bootcamp.productservice.application.service.AccountService;
import com.nttdata.bootcamp.productservice.domain.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
public class AccountWebService implements AccountService {

    private static final String WEB_CLIENT_URL = "account.web.url";
    private static final String URI = "/accounts";
    private final WebClient webClient;

    @Autowired
    public AccountWebService(WebClient.Builder webClientBuilder, Environment env) {
        this.webClient = webClientBuilder.baseUrl(env.getProperty(WEB_CLIENT_URL)).build();
    }


    @Override
    public Flux<Account> getAll() {
        return this.webClient.get().uri(URI)
                .retrieve().bodyToFlux(Account.class);
    }

    @Override
    public Mono<Account> get(String id) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .retrieve().bodyToMono(Account.class);
    }

    @Override
    public Mono<Account> create(Account account) {
        return this.webClient.post().uri(URI)
                .body(Mono.justOrEmpty(account), Account.class)
                .retrieve().bodyToMono(Account.class);
    }

    @Override
    public Mono<Account> update(String id, Account account) {
        return this.webClient.put()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .body(Mono.justOrEmpty(account), Account.class)
                .retrieve().bodyToMono(Account.class);
    }


    @Override
    public Mono<Void> delete(String id) {
        return this.webClient.delete()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .retrieve().bodyToMono(Void.class);
    }
}
