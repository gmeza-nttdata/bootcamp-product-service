package com.nttdata.bootcamp.productservice.application.impl;

import com.nttdata.bootcamp.productservice.application.ProductOperations;
import com.nttdata.bootcamp.productservice.application.repository.ProductRepository;
import com.nttdata.bootcamp.productservice.application.service.AccountService;
import com.nttdata.bootcamp.productservice.application.service.CreditService;
import com.nttdata.bootcamp.productservice.application.service.UserService;
import com.nttdata.bootcamp.productservice.domain.entity.Product;
import com.nttdata.bootcamp.productservice.domain.entity.ProductType;
import com.nttdata.bootcamp.productservice.domain.entity.user.User;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.AccountDto;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.BalanceDto;
import com.nttdata.bootcamp.productservice.domain.entity.credit.Credit;
import com.nttdata.bootcamp.productservice.infrastructure.model.dto.CreditDto;
import com.nttdata.bootcamp.productservice.domain.entity.account.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOperationsImpl implements ProductOperations {

    private final AccountService accountService;
    private final CreditService creditService;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Override
    public Mono<Account> createAccount(AccountDto accountDto) {
        return userService.get(accountDto.getUserId())
            .flatMap(user -> accountService.getAll()
                // Getting Accounts of the same type
                .filter(item -> item.getUserId().equals(user.getId()) && item.getType().equals(accountDto.getType()))
                .collectList()
                // Getting the product(s) correspondent to the account
                .flatMap(accounts -> creditService.getAll()
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
                        }).flatMap(accountService::create))
                )
                );
    }

    @Override
    public Mono<Credit> createCredit(CreditDto creditDto) {
        return userService.get(creditDto.getUserId())
                .flatMap(user -> creditService.getAll()
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
                            .flatMap(creditService::create))
                        );
    }

    @Override
    public Mono<BalanceDto> getCreditBalance(String number) {
        return creditService.get(number).map(BalanceDto::mapCreditToBalance);
    }

    @Override
    public Mono<BalanceDto> getAccountBalance(String number) {
        return accountService.get(number).map(BalanceDto::mapAccountToBalance);
    }

    @Override
    public Flux<Product> getAvailableProducts(Integer userId) {

        Mono<List<Account>> userAccountsMono = accountService.getAll()
                .filter(item -> item.getUserId().equals(userId)).collectList();
        Mono<List<Credit>> userCreditsMono = creditService.getAll()
                .filter(item -> item.getUserId().equals(userId)).collectList();

        return Mono.zip(userAccountsMono, userCreditsMono, userService.get(userId))
                .flatMapMany(tuple -> {
                    List<Account> accounts = tuple.getT1();
                    List<Credit> credits = tuple.getT2();
                    User user = tuple.getT3();

                    return productRepository.getAll()
                            .filter(p -> p.getUserType().equals(user.getType()))
                            .filter(p -> (p.getRequiresCreditCardUponCreation() == null) || !p.getRequiresCreditCardUponCreation() || !credits.isEmpty())
                            .filter(p -> filterByMaxForUser(p, accounts, credits));
                });

    }

    private boolean filterByMaxForUser(Product p, List<Account> accounts, List<Credit> credits) {
        switch (p.getProductType()) {
            case ACCOUNT:
                return p.getMaxForUser() > accounts.stream()
                        .filter(a -> p.getProductSubtype().equals(a.getType().toString())).count();
            case CREDIT:
                return p.getMaxForUser() > credits.size();
            default:
                throw  new IllegalArgumentException("Bad ProductType in Product: " + p.toString());
        }
    }


    private Boolean filterAccounts(Product p, User user, AccountDto accountDto) {

        return p.getProductType().equals(ProductType.ACCOUNT) &&
                p.getUserType().equals(user.getType()) &&
                p.getProductSubtype().equals(accountDto.getType().toString());
    }

}
