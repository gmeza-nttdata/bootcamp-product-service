package com.nttdata.bootcamp.project1.products.application.service;

import com.nttdata.bootcamp.project1.products.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService extends IService<User,Integer> {
}
