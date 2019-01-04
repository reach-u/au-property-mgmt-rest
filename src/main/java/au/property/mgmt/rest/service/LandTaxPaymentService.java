package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.DTO.TaxAreaStatsDTO;
import au.property.mgmt.rest.model.LandTaxPayment;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface LandTaxPaymentService {
    LandTaxPayment pay(long id) throws InstanceNotFoundException;

    void generatePayments();

    LandTaxPayment[] getAllPayments();

    List<TaxAreaStatsDTO> getAreaStatistics();
}
