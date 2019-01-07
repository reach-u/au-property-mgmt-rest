package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.DTO.TaxStatsDTO;
import au.property.mgmt.rest.model.DTO.UserPaymentsDTO;
import au.property.mgmt.rest.model.LandTaxPayment;

import javax.management.InstanceNotFoundException;
import java.util.List;

public interface LandTaxPaymentService {
    LandTaxPayment pay(long id) throws InstanceNotFoundException;

    void generatePayments();

    List<UserPaymentsDTO> getPaymentsByUser(long userId);

    List<TaxStatsDTO> getAreaStatistics();

    List<TaxStatsDTO> getMonthlyStatistics();

    LandTaxPayment[] getAllPayments();
}
