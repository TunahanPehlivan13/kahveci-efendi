package com.ke.model.embedded;

import lombok.Data;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Access(AccessType.FIELD)
@Data
public class DiscountDetail {

    @Basic
    private BigDecimal totalDiscount = BigDecimal.ZERO;

    @Basic
    private String discountProvider;
}
