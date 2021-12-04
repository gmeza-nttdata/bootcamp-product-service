package com.nttdata.bootcamp.productservice.infrastructure.localrepository;

import com.nttdata.bootcamp.productservice.application.repository.ProductRepository;
import com.nttdata.bootcamp.productservice.domain.entity.CurrencyType;
import com.nttdata.bootcamp.productservice.domain.entity.Product;
import com.nttdata.bootcamp.productservice.domain.entity.ProductType;
import com.nttdata.bootcamp.productservice.domain.entity.account.AccountContract;
import com.nttdata.bootcamp.productservice.domain.entity.account.AccountType;
import com.nttdata.bootcamp.productservice.domain.entity.user.UserType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class LocalProductRepository implements ProductRepository {

    private static final int ACCOUNT_MONTHLY_TRANSACTION_LIMIT = 5;
    private static final int ACCOUNT_DAILY_TRANSACTION_LIMIT = 1;
    private static final BigDecimal ACCOUNT_INITIAL_BALANCE = BigDecimal.ZERO;
    private static final BigDecimal ACCOUNT_TRANSACTION_FEE = BigDecimal.valueOf(0.5);
    private static final BigDecimal ACCOUNT_MONTHLY_MAINTENANCE_FEE = BigDecimal.TEN;

    private static final BigDecimal CREDIT_INITIAL_CREDIT_LINE = BigDecimal.valueOf(5000);

    private static final BigDecimal ACCOUNT_DAILY_AVERAGE_BALANCE_LIMIT = BigDecimal.valueOf(100);

    private static final ArrayList<Product> PRODUCTS = new ArrayList<>()
    {{
        // ACCOUNTS
        add(Product.builder()
                .id("1")
                .productType(ProductType.ACCOUNT)
                .userType(UserType.PERSONAL)
                .productSubtype(AccountType.SAVINGS.toString())
                .initialBalance(BigDecimal.ZERO)
                .currencyName(CurrencyType.PEN.toString())
                .requiresCreditCardUponCreation(Boolean.FALSE)
                .maxForUser(1)
                .accountContract(AccountContract.builder()
                        .hasMonthlyMaintenanceFee(Boolean.FALSE)
                        .hasMinimumInitialBalance(Boolean.TRUE)
                        .minimumInitialBalance(ACCOUNT_INITIAL_BALANCE)
                        .hasMonthlyTransactionLimit(Boolean.TRUE)
                        .monthlyTransactionLimit(ACCOUNT_MONTHLY_TRANSACTION_LIMIT)
                        .transactionFeeAfterLimit(ACCOUNT_TRANSACTION_FEE)
                        .build())
                .build());
        add(Product.builder()
                .id("2")
                .productType(ProductType.ACCOUNT)
                .userType(UserType.PERSONAL)
                .productSubtype(AccountType.CURRENT.toString())
                .initialBalance(BigDecimal.ZERO)
                .currencyName(CurrencyType.PEN.toString())
                .requiresCreditCardUponCreation(Boolean.FALSE)
                .maxForUser(1)
                .accountContract(AccountContract.builder()
                        .hasMonthlyMaintenanceFee(Boolean.TRUE)
                        .monthlyMaintenanceFee(ACCOUNT_MONTHLY_MAINTENANCE_FEE)
                        .hasMinimumInitialBalance(Boolean.TRUE)
                        .minimumInitialBalance(ACCOUNT_INITIAL_BALANCE)
                        .hasMonthlyTransactionLimit(Boolean.TRUE)
                        .monthlyTransactionLimit(ACCOUNT_MONTHLY_TRANSACTION_LIMIT)
                        .transactionFeeAfterLimit(ACCOUNT_TRANSACTION_FEE)
                        .build())
                .build());
        add(Product.builder()
                .id("3")
                .productType(ProductType.ACCOUNT)
                .userType(UserType.PERSONAL)
                .productSubtype(AccountType.FIXED_TERM.toString())
                .initialBalance(BigDecimal.ZERO)
                .currencyName(CurrencyType.PEN.toString())
                .requiresCreditCardUponCreation(Boolean.FALSE)
                .maxForUser(1)
                .accountContract(AccountContract.builder()
                        .hasMonthlyMaintenanceFee(Boolean.FALSE)
                        .hasMinimumInitialBalance(Boolean.TRUE)
                        .minimumInitialBalance(ACCOUNT_INITIAL_BALANCE)
                        .hasDailyTransactionLimit(Boolean.TRUE)
                        .dailyTransactionLimit(ACCOUNT_DAILY_TRANSACTION_LIMIT)
                        .transactionFeeAfterLimit(ACCOUNT_TRANSACTION_FEE)
                        .build())
                .build());
        add(Product.builder()
                .id("4")
                .productType(ProductType.ACCOUNT)
                .userType(UserType.BUSINESS)
                .productSubtype(AccountType.CURRENT.toString())
                .initialBalance(BigDecimal.ZERO)
                .currencyName(CurrencyType.PEN.toString())
                .requiresCreditCardUponCreation(Boolean.FALSE)
                .maxForUser(Integer.MAX_VALUE)
                .accountContract(AccountContract.builder()
                        .hasMonthlyMaintenanceFee(Boolean.TRUE)
                        .monthlyMaintenanceFee(ACCOUNT_MONTHLY_MAINTENANCE_FEE)
                        .hasMinimumInitialBalance(Boolean.TRUE)
                        .minimumInitialBalance(ACCOUNT_INITIAL_BALANCE)
                        .hasMonthlyTransactionLimit(Boolean.TRUE)
                        .monthlyTransactionLimit(ACCOUNT_MONTHLY_TRANSACTION_LIMIT)
                        .transactionFeeAfterLimit(ACCOUNT_TRANSACTION_FEE)
                        .build())
                .build());

        // CREDIT
        add(Product.builder()
                .id("5")
                .productType(ProductType.CREDIT)
                .hasCreditCard(Boolean.FALSE)
                .userType(UserType.PERSONAL)
                .initialCreditLine(CREDIT_INITIAL_CREDIT_LINE)
                .currencyName(CurrencyType.PEN.toString())
                .maxForUser(1)
                .build());
        add(Product.builder()
                .id("6")
                .productType(ProductType.CREDIT)
                .hasCreditCard(Boolean.FALSE)
                .userType(UserType.BUSINESS)
                .initialCreditLine(CREDIT_INITIAL_CREDIT_LINE)
                .currencyName(CurrencyType.PEN.toString())
                .maxForUser(Integer.MAX_VALUE)
                .build());

        // NEW FOR PROJECT 2
        add(Product.builder()
                .id("7")
                .productType(ProductType.ACCOUNT)
                .userType(UserType.PERSONAL)
                .productSubtype(AccountType.SAVINGS_VIP.toString())
                .initialBalance(BigDecimal.ZERO)
                .currencyName(CurrencyType.PEN.toString())
                .requiresCreditCardUponCreation(Boolean.TRUE)
                .maxForUser(1)
                .accountContract(AccountContract.builder()
                        .hasMonthlyMaintenanceFee(Boolean.FALSE)
                        .hasMinimumInitialBalance(Boolean.TRUE)
                        .minimumInitialBalance(ACCOUNT_INITIAL_BALANCE)
                        .hasMonthlyTransactionLimit(Boolean.TRUE)
                        .monthlyTransactionLimit(ACCOUNT_MONTHLY_TRANSACTION_LIMIT)
                        .transactionFeeAfterLimit(ACCOUNT_TRANSACTION_FEE)
                        .requiresDailyAverageBalance(Boolean.TRUE)
                        .dailyAverageBalanceLimit(ACCOUNT_DAILY_AVERAGE_BALANCE_LIMIT)
                        .build())
                .build());

        add(Product.builder()
                .id("8")
                .productType(ProductType.ACCOUNT)
                .userType(UserType.BUSINESS)
                .productSubtype(AccountType.CURRENT_PYME.toString())
                .initialBalance(BigDecimal.ZERO)
                .currencyName(CurrencyType.PEN.toString())
                .requiresCreditCardUponCreation(Boolean.TRUE)
                .maxForUser(Integer.MAX_VALUE)
                .accountContract(AccountContract.builder()
                        .hasMonthlyMaintenanceFee(Boolean.FALSE)
                        .monthlyMaintenanceFee(ACCOUNT_MONTHLY_MAINTENANCE_FEE)
                        .hasMinimumInitialBalance(Boolean.TRUE)
                        .minimumInitialBalance(ACCOUNT_INITIAL_BALANCE)
                        .hasMonthlyTransactionLimit(Boolean.TRUE)
                        .monthlyTransactionLimit(ACCOUNT_MONTHLY_TRANSACTION_LIMIT)
                        .transactionFeeAfterLimit(ACCOUNT_TRANSACTION_FEE)
                        .build())
                .build());

    }};

    @Override
    public Flux<Product> getAll() {
        return Flux.fromIterable(PRODUCTS);
    }

    @Override
    public Mono<Product> getById() {
        return null;
    }

    @Override
    public Mono<Product> create() {
        return null;
    }

    @Override
    public Mono<Product> update() {
        return null;
    }

    @Override
    public Mono<Void> delete() {
        return null;
    }
}
