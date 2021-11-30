package com.nttdata.bootcamp.productservice.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountContract {

    /** hasMaintenanceFee? */
    private Boolean hasMaintenanceFee;
    /** maintenanceFee. */
    private BigDecimal maintenanceFee;

    /** hasMonthlyOperationLimit? */
    private Boolean hasMonthlyOperationLimit;
    /** monthlyOperationLimit. */
    private BigDecimal monthlyOperationLimit;

    /** hasDailyOperationLimit? */
    private Boolean hasDailyOperationLimit;
    /** dailyOperationLimit. */
    private BigDecimal dailyOperationLimit;

}
