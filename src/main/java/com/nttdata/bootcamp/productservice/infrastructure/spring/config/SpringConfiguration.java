package com.nttdata.bootcamp.productservice.infrastructure.spring.config;

import com.nttdata.bootcamp.productservice.application.repository.ProductRepository;
import com.nttdata.bootcamp.productservice.infrastructure.localrepository.LocalProductRepository;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SpringConfiguration {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public ProductRepository productRepository() {
        return new LocalProductRepository();
    }
}
