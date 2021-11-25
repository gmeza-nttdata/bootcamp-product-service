package com.nttdata.bootcamp.productservice.infrastructure.service;

import com.nttdata.bootcamp.productservice.application.service.CreditService;
import com.nttdata.bootcamp.productservice.domain.entity.Credit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CreditWebService implements CreditService {

    @Autowired
    private WebClient.Builder webClientBuilder;
    private static final String WEB_CLIENT_URL = "credit.web.url";
    private final String URI;

    @Autowired
    public CreditWebService(Environment env) {
        URI = env.getProperty(WEB_CLIENT_URL);
    }

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
}
