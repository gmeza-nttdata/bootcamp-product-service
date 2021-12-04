package com.nttdata.bootcamp.productservice.domain.entity.account;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
public class AccountContract {

    /** hasMaintenanceFee? */
    private Boolean hasMonthlyMaintenanceFee = Boolean.FALSE;
    /** maintenanceFee. */
    private BigDecimal monthlyMaintenanceFee;

    /** hasDailyOperationLimit? */
    private Boolean hasDailyTransactionLimit = Boolean.FALSE;
    /** dailyOperationLimit. */
    private Integer dailyTransactionLimit;

    private Boolean hasMinimumInitialBalance = Boolean.FALSE;
    private BigDecimal minimumInitialBalance;

    /** hasMonthlyOperationLimit? */
    private Boolean hasMonthlyTransactionLimit = Boolean.FALSE;
    /** monthlyOperationLimit. */
    private Integer monthlyTransactionLimit;

    /** Fee for transactions after limit. */
    private BigDecimal transactionFeeAfterLimit;

    /** Additional DailyAverageBalance */
    private Boolean requiresDailyAverageBalance = Boolean.FALSE;
    private BigDecimal dailyAverageBalanceLimit;


}
