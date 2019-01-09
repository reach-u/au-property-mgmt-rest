package au.property.mgmt.rest.model.DTO;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.LandTaxPayment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
public class MonthlyTaxStatsDTO extends TaxStatsDTO {

    private List<TaxStatsDTO> indebtedOwners;
    private Map<Long, Set<Address>> debtorAddresses;

    public MonthlyTaxStatsDTO(String name, List<LandTaxPayment> payments) {
        super(name, payments);
        this.indebtedOwners = this.calculateIndebtedOwners(payments);
        this.debtorAddresses = this.collectDebtorAddresses(payments);
    }

    private List<TaxStatsDTO> calculateIndebtedOwners(List<LandTaxPayment> payments) {
        if (this.getMissingAmount().equals(BigDecimal.ZERO)) {
            return Collections.emptyList();
        }

        Map<Long, List<LandTaxPayment>> paymentsGroupedByOwner = payments.stream()
                .collect(Collectors.groupingBy(LandTaxPayment::getOwnerIdCode));

        return paymentsGroupedByOwner
                .entrySet().stream()
                .map(entry -> new TaxStatsDTO(String.valueOf(entry.getKey()), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Map<Long, Set<Address>> collectDebtorAddresses(List<LandTaxPayment> payments) {
        if (this.getMissingAmount().equals(BigDecimal.ZERO)) {
            return Collections.emptyMap();
        }

        Map<Long, List<LandTaxPayment>> paymentsGroupedByOwner = payments.stream()
                .collect(Collectors.groupingBy(LandTaxPayment::getOwnerIdCode));

        return paymentsGroupedByOwner.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> getOwnerAddresses(entry.getKey(), payments)
        ));
    }

    private Set<Address> getOwnerAddresses(Long ownerId, List<LandTaxPayment> payments) {
        return payments.stream()
                .filter(payment -> payment.getOwnerIdCode() == ownerId)
                .map(LandTaxPayment::getAddress)
                .collect(Collectors.toSet());
    }

}
