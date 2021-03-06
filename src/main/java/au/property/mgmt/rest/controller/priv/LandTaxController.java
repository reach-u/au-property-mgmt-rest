package au.property.mgmt.rest.controller.priv;

import au.property.mgmt.rest.model.DTO.TaxStatsDTO;
import au.property.mgmt.rest.model.DTO.UserPaymentsDTO;
import au.property.mgmt.rest.model.LandTaxPayment;
import au.property.mgmt.rest.model.LandTaxZone;
import au.property.mgmt.rest.service.LandTaxPaymentService;
import au.property.mgmt.rest.service.LandTaxZoneService;
import au.property.mgmt.rest.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.List;

@RestController
@RequestMapping(Constants.PRIVATE_API_V1_URL + "/landtax")
public class LandTaxController {

    @Autowired
    private LandTaxPaymentService landTaxPaymentService;

    @Autowired
    private LandTaxZoneService landTaxZoneService;

    @GetMapping(value = "zones")
    public LandTaxZone[] getAllZones() {
        return landTaxZoneService.getAllZones();
    }

    @PostMapping(value = "pay/{id}")
    public LandTaxPayment pay(@PathVariable long id) throws InstanceNotFoundException {
        return landTaxPaymentService.pay(id);
    }

    @GetMapping(value = "payments/{userId}")
    public List<UserPaymentsDTO> getUsersPayments(@PathVariable long userId) {
        return landTaxPaymentService.getPaymentsByUser(userId);
    }

    @GetMapping(value = "payments")
    public LandTaxPayment[] getPayments() {
        return landTaxPaymentService.getAllPayments();
    }

    @GetMapping(value = "/stats/area")
    public List<TaxStatsDTO> getAreaStatistics() {
        return landTaxPaymentService.getAreaStatistics();
    }

    @GetMapping(value = "/stats/month")
    public List<TaxStatsDTO> getMonthlyStatistics() {
        return landTaxPaymentService.getMonthlyStatistics();
    }
}
