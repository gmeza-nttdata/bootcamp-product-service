package com.nttdata.bootcamp.productservice.infrastructure.service;

import com.nttdata.bootcamp.productservice.application.service.UserService;
import com.nttdata.bootcamp.productservice.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UserWebService implements UserService {

    private final WebClient.Builder webClientBuilder;
    private final String URI;

    @Autowired
    public UserWebService(WebClient.Builder webClientBuilder,
                          @Value("${user.web.url}") String URI) {
        this.webClientBuilder = webClientBuilder;
        this.URI = URI;
    }

    @Override
    public Flux<User> getAll() {
        return webClientBuilder.build().get().uri(URI)
                .retrieve().bodyToFlux(User.class);
    }

    @Override
    public Mono<User> get(Integer id) {
        return webClientBuilder.build().get()
                .uri(URI + "/{id}", id)
                .retrieve().bodyToMono(User.class);
    }

    @Override
    public Mono<User> create(User user) {
        return webClientBuilder.build().post().uri(URI)
                .body(Mono.justOrEmpty(user), User.class)
                .retrieve().bodyToMono(User.class);
    }

    @Override
    public Mono<User> update(Integer id, User user) {
        return webClientBuilder.build().put()
                .uri(URI + "/{id}", id)
                .body(Mono.justOrEmpty(user), User.class)
                .retrieve().bodyToMono(User.class);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return webClientBuilder.build().delete()
                .uri(URI + "/{id}", id)
                .retrieve().bodyToMono(Void.class);
    }



}
