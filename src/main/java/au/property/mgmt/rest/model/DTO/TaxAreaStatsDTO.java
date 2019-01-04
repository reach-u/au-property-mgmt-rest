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
public class TaxAreaStatsDTO {

    private String name;

    private int numberOfSquareMeters;

    private BigDecimal plannedAmount;

    private BigDecimal paidAmount;

    private BigDecimal missingAmount;

    public TaxAreaStatsDTO(String zoneName, List<LandTaxPayment> payments) {
        this.name = zoneName;
        this.numberOfSquareMeters = calculateZoneLandAcreage(payments);
        this.plannedAmount = calculateZonePlannedAmount(payments);
        this.paidAmount = calculateZonePaidAmount(payments);
        this.missingAmount = calculateZoneMissingAmount(payments);
    }

    private BigDecimal calculateZoneMissingAmount(List<LandTaxPayment> zonePayments) {
        return zonePayments.stream()
                .filter(payment -> !payment.isPaid())
                .map(LandTaxPayment::getPayable)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateZonePaidAmount(List<LandTaxPayment> zonePayments) {
        return zonePayments.stream()
                .filter(LandTaxPayment::isPaid)
                .map(LandTaxPayment::getPayable)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateZonePlannedAmount(List<LandTaxPayment> zonePayments) {
        return zonePayments.stream()
                .map(LandTaxPayment::getPayable)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer calculateZoneLandAcreage(List<LandTaxPayment> zonePayments) {
        return zonePayments.stream()
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
