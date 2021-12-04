package com.nttdata.bootcamp.productservice.application.impl;

import com.nttdata.bootcamp.productservice.application.ProductOperations;
import com.nttdata.bootcamp.productservice.application.repository.ProductRepository;
import com.nttdata.bootcamp.productservice.domain.entity.Product;
import com.nttdata.bootcamp.productservice.domain.entity.ProductType;
import com.nttdata.bootcamp.productservice.domain.entity.user.User;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.AccountDto;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.BalanceDto;
import com.nttdata.bootcamp.productservice.domain.entity.credit.Credit;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.CreditDto;
import com.nttdata.bootcamp.productservice.infrastructure.service.AccountWebService;
import com.nttdata.bootcamp.productservice.infrastructure.service.CreditWebService;
import com.nttdata.bootcamp.productservice.infrastructure.service.UserWebService;
import com.nttdata.bootcamp.productservice.domain.entity.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOperationsImpl implements ProductOperations {

    private final AccountWebService accountWebService;
    private final CreditWebService creditWebService;
    private final UserWebService userWebService;
    private final ProductRepository productRepository;

    @Override
    public Mono<Account> createAccount(AccountDto accountDto) {
        return userWebService.get(accountDto.getUserId())
            .flatMap(user -> accountWebService.getAll()
                // Getting Accounts of the same type
                .filter(item -> item.getUserId().equals(user.getId()) && item.getType().equals(accountDto.getType()))
                .collectList()
                // Getting the product(s) correspondent to the account
                .flatMap(accounts -> creditWebService.getAll()
                    .filter(credit -> credit.getUserId().equals(user.getId()) && credit.getHasCard())
                    .collectList()
                    .flatMap(credits -> productRepository.getAll()
                        .filter(p -> this.filterAccounts(p, user, accountDto))
                        .collectList()
                        .flatMap(products -> {
                            if (products.isEmpty())
                                throw new IllegalArgumentException("This product is not available for User");
                            return Flux.fromIterable(products).next()
                                .map(product -> {
                                    log.info(product.toString());
                                    if (product.getMaxForUser() <= accounts.size())
                                        throw new IllegalArgumentException("Max Accounts reached for this account type");

                                    if (product.getRequiresCreditCardUponCreation() && credits.isEmpty())
                                        throw new IllegalArgumentException("This product requires a credit card upon creation");

                                    // If initial balance is less than the specified in the contract
                                    if (product.wrongInitialBalance(accountDto))
                                        throw new IllegalArgumentException("Wrong initial balance, must be at least: " +
                                                product.getAccountContract().getMinimumInitialBalance().toString());

                                    return Product.createAccount(accountDto, product);
                                });
                        }).flatMap(accountWebService::create))
                )
                );
    }

    @Override
    public Mono<Credit> createCredit(CreditDto creditDto) {
        return userWebService.get(creditDto.getUserId())
                .flatMap(user -> creditWebService.getAll()
                        .filter(item -> item.getUserId().equals(user.getId()))
                        .collectList()
                        .flatMap(credits -> productRepository.getAll()
                            .filter(p -> p.getProductType().equals(ProductType.CREDIT) &&
                                        p.getUserType().equals(user.getType())
                                    )
                            .next()
                            .map(product -> {
                                if (product.getMaxForUser() <= credits.size())
                                    throw new IllegalArgumentException("Max Credits reached for this user");
                                return Product.createCredit(creditDto, product);
                            })
                            .flatMap(creditWebService::create))
                        );
    }

    @Override
    public Mono<BalanceDto> getCreditBalance(String number) {
        return creditWebService.get(number).map(BalanceDto::mapCreditToBalance);
    }

    @Override
    public Mono<BalanceDto> getAccountBalance(String number) {
        return accountWebService.get(number).map(BalanceDto::mapAccountToBalance);
    }

    private Boolean filterAccounts(Product p, User user, AccountDto accountDto) {

        return p.getProductType().equals(ProductType.ACCOUNT) &&
                p.getUserType().equals(user.getType()) &&
                p.getProductSubtype().equals(accountDto.getType().toString());
    }

}
