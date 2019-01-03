package au.property.mgmt.rest.controller.priv;

import au.property.mgmt.rest.model.LandTaxPayment;
import au.property.mgmt.rest.model.LandTaxZone;
import au.property.mgmt.rest.service.LandTaxPaymentService;
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

    @GetMapping(value = "zones")
    public List<LandTaxZone> getAllZones() {
        return null;
    }

    @PostMapping(value="pay/{id}")
    public LandTaxPayment pay(@PathVariable long id) throws InstanceNotFoundException {
        return landTaxPaymentService.pay(id);
    }

}
