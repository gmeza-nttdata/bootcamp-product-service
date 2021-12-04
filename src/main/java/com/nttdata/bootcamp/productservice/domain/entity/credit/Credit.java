package com.nttdata.bootcamp.productservice.domain.entity.credit;

import com.nttdata.bootcamp.productservice.domain.entity.user.UserType;
import com.nttdata.bootcamp.productservice.domain.entity.user.User;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    private String number;
    private Integer userId;
    private Boolean hasCard;
    private String currencyName;
    private BigDecimal balance;

    private BigDecimal creditLine;
    private BigDecimal rate;
    private Integer cutoffDate;
    private Integer paymentDate;

}
