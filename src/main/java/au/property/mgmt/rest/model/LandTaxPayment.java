package au.property.mgmt.rest.model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class LandTaxPayment {
    private long id;
    private long ownerIdCode;
    private Address address;
    private BigDecimal payable;
    private Date dueDate;
    private boolean isPaid;
}
