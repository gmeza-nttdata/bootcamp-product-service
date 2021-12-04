package com.nttdata.bootcamp.productservice.application.service;

import com.nttdata.bootcamp.productservice.domain.entity.credit.Credit;
import org.springframework.stereotype.Component;

@Component
public interface CreditService extends IService<Credit,String> {
}
