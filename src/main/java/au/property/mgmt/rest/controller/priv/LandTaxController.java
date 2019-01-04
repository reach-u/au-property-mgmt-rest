package au.property.mgmt.rest.controller.priv;

import au.property.mgmt.rest.model.DTO.TaxAreaStatsDTO;
import au.property.mgmt.rest.model.LandTaxPayment;
import au.property.mgmt.rest.model.LandTaxZone;
import au.property.mgmt.rest.service.LandTaxPaymentService;
import au.property.mgmt.rest.service.LandTaxZoneService;
import au.property.mgmt.rest.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;
import java.util.List;
import java.util.Arrays;

@Slf4j
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
    public ResponseEntity<LandTaxPayment[]> getUsersPayments(@PathVariable long userId) {
        log.info("find payments: person id={}", userId);
        return ResponseEntity.ok(Arrays.stream(landTaxPaymentService.getAllPayments())
                .filter(payment -> payment.getOwnerIdCode() == userId).toArray(LandTaxPayment[]::new));
    }

    @GetMapping(value = "payments")
    public LandTaxPayment[] getPayments() {
        return landTaxPaymentService.getAllPayments();
    }

    @GetMapping(value = "/stats/area")
    public List<TaxAreaStatsDTO> getAreaStatistics() {
        return landTaxPaymentService.getAreaStatistics();
    }
}
