package com.nttdata.bootcamp.project1.products.infrastructure.service;

import com.nttdata.bootcamp.project1.products.application.service.CreditService;
import com.nttdata.bootcamp.project1.products.domain.entity.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CreditWebService implements CreditService {

    private static final String WEB_CLIENT_URL = "credit.web.url";
    private static final String URI = "/credits";
    private final WebClient webClient;

    @Autowired
    public CreditWebService(WebClient.Builder webClientBuilder, Environment env) {
        this.webClient = webClientBuilder.baseUrl(env.getProperty(WEB_CLIENT_URL)).build();
    }

    @Override
    public Flux<Credit> getAll() {
        return this.webClient.get().uri(URI)
                .retrieve().bodyToFlux(Credit.class);
    }

    @Override
    public Mono<Credit> get(String id) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .retrieve().bodyToMono(Credit.class);
    }

    @Override
    public Mono<Credit> create(Credit credit) {
        return this.webClient.post().uri(URI)
                .body(Mono.justOrEmpty(credit), Credit.class)
                .retrieve().bodyToMono(Credit.class);
    }

    @Override
    public Mono<Credit> update(String id, Credit credit) {
        return this.webClient.put()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .body(Mono.justOrEmpty(credit), Credit.class)
                .retrieve().bodyToMono(Credit.class);
    }

    @Override
    public Mono<Void> delete(String id) {
        return this.webClient.delete()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .retrieve().bodyToMono(Void.class);
    }
}
