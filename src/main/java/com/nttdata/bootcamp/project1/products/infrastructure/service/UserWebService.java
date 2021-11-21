package com.nttdata.bootcamp.project1.products.infrastructure.service;

import com.nttdata.bootcamp.project1.products.application.service.UserService;
import com.nttdata.bootcamp.project1.products.domain.entity.User;
import com.nttdata.bootcamp.project1.products.infrastructure.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    private User mapClientDaoToClient(UserDao userDao) {
        User user = new User();
        user.setAddress(userDao.getAddress());
        user.setType(userDao.getType());
        user.setFullName(userDao.getFullName());
        user.setBirthDate(userDao.getBirthDate());
        user.setId(userDao.getId());
        return user;
    }

    private UserDao mapClientToClientDao(User user) {
        UserDao userDao = new UserDao();
        userDao.setAddress(user.getAddress());
        userDao.setType(user.getType());
        userDao.setFullName(user.getFullName());
        userDao.setBirthDate(user.getBirthDate());
        userDao.setId(user.getId());
        return userDao;
    }

}
