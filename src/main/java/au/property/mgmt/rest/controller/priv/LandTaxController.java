package au.property.mgmt.rest.controller.priv;

import au.property.mgmt.rest.model.LandTaxPayment;
import au.property.mgmt.rest.model.LandTaxZone;
import au.property.mgmt.rest.service.LandTaxPaymentService;
import au.property.mgmt.rest.service.LandTaxZoneService;
import au.property.mgmt.rest.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceNotFoundException;

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
}
