package com.nttdata.bootcamp.project1.products.infrastructure.model.dao;

import com.nttdata.bootcamp.project1.products.domain.dto.OperationType;
import com.nttdata.bootcamp.project1.products.domain.dto.ProductType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document("Statement")
public class StatementDao {

    private String id;
    private ProductType productType;
    private String number;
    private OperationType operation;
    private BigDecimal amount;
    private LocalDateTime dateTime;
}
