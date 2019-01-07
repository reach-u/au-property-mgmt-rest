package au.property.mgmt.rest.model.DTO;

import au.property.mgmt.rest.model.LandTaxPayment;
import lombok.Data;

import java.util.List;

@Data
public class UserPaymentsDTO {

    private String monthName;

    private List<LandTaxPayment> payments;

    public UserPaymentsDTO(String monthName, List<LandTaxPayment> payments) {
        this.monthName = monthName;
        payments.forEach(payment -> payment.getAddress().setDetailedData(null));
        this.payments = payments;
    }
}
