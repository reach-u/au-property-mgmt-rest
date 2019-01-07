package au.property.mgmt.rest.model.DTO;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.DetailedData;
import au.property.mgmt.rest.model.LandTaxPayment;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Data
public class TaxStatsDTO {

    private String name;

    private int numberOfSquareMeters;

    private BigDecimal plannedAmount;

    private BigDecimal paidAmount;

    private BigDecimal missingAmount;

    public TaxStatsDTO(String name, List<LandTaxPayment> payments) {
        this.name = name;
        this.numberOfSquareMeters = calculateZoneLandAcreage(payments);
        this.plannedAmount = calculatePlannedAmount(payments);
        this.paidAmount = calculatePaidAmount(payments);
        this.missingAmount = calculateMissingAmount(payments);
    }

    private BigDecimal calculateMissingAmount(List<LandTaxPayment> payments) {
        return payments.stream()
                .filter(payment -> !payment.isPaid())
                .map(LandTaxPayment::getPayable)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePaidAmount(List<LandTaxPayment> payments) {
        return payments.stream()
                .filter(LandTaxPayment::isPaid)
                .map(LandTaxPayment::getPayable)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePlannedAmount(List<LandTaxPayment> payments) {
        return payments.stream()
                .map(LandTaxPayment::getPayable)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer calculateZoneLandAcreage(List<LandTaxPayment> payments) {
        return payments.stream()
                .map(LandTaxPayment::getAddress)
                .filter(distinctByKey(Address::getCoordinates))
                .map(Address::getDetailedData)
                .map(DetailedData::getLandAcreage)
                .reduce(0, (a, b) -> a + b);
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
