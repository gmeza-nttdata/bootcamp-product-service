package com.nttdata.bootcamp.productservice.application.service;

import com.nttdata.bootcamp.productservice.domain.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService extends IService<User,Integer> {
}
