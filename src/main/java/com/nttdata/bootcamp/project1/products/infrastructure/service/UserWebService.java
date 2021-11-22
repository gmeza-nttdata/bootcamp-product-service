package com.nttdata.bootcamp.project1.products.infrastructure.service;

import com.nttdata.bootcamp.project1.products.application.service.UserService;
import com.nttdata.bootcamp.project1.products.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserWebService implements UserService {

    private static final String WEB_CLIENT_URL = "user.web.url";
    private static final String URI = "/users";
    private final WebClient webClient;

    @Autowired
    public UserWebService(WebClient.Builder webClientBuilder, Environment env) {
        this.webClient = webClientBuilder.baseUrl(env.getProperty(WEB_CLIENT_URL)).build();
    }


    @Override
    public Flux<User> getAll() {
        return this.webClient.get().uri(URI)
                .retrieve().bodyToFlux(User.class);
    }

    @Override
    public Mono<User> get(Integer id) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .retrieve().bodyToMono(User.class);
    }

    @Override
    public Mono<User> create(User user) {
        return this.webClient.post().uri(URI)
                .body(Mono.justOrEmpty(user), User.class)
                .retrieve().bodyToMono(User.class);
    }

    @Override
    public Mono<User> update(Integer id, User user) {
        return this.webClient.put()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .body(Mono.justOrEmpty(user), User.class)
                .retrieve().bodyToMono(User.class);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return this.webClient.delete()
                .uri(uriBuilder -> uriBuilder.path(URI + "/{id}").build(id))
                .retrieve().bodyToMono(Void.class);
    }



}
