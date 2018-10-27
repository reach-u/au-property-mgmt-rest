package au.property.mgmt.rest.controller.priv;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Deal;
import au.property.mgmt.rest.service.AddressService;
import au.property.mgmt.rest.service.RealEstateService;
import au.property.mgmt.rest.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taaviv @ 26.10.18
 */
@RestController
@RequestMapping(Constants.PRIVATE_API_V1_URL + "/re")
public class RealEstateController {

    private final RealEstateService realEstateService;

    private final AddressService addressService;

    public RealEstateController(RealEstateService realEstateService, AddressService addressService) {
        this.realEstateService = realEstateService;
        this.addressService = addressService;
    }

    @RequestMapping(value = "buy/{buyerIdCode}/{addressId}", method = RequestMethod.POST)
    public ResponseEntity<Deal> buy(@PathVariable long buyerIdCode, @PathVariable long addressId) {
        Address address = addressService.search(addressId);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(realEstateService.buy(buyerIdCode, address));
    }

    @RequestMapping(value = "sign_by_buyer/{transactionId}", method = RequestMethod.POST)
    public ResponseEntity<Deal> signByBuyer(@PathVariable long transactionId) {
        Deal deal = realEstateService.signByBuyer(transactionId);
        return deal != null ? ResponseEntity.ok(deal) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "sign_by_seller/{transactionId}", method = RequestMethod.POST)
    public ResponseEntity<Deal> signBySeller(@PathVariable long transactionId) {
        Deal deal = realEstateService.signBySeller(transactionId);
        return deal != null ? ResponseEntity.ok(deal) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "details/{transactionId}", method = RequestMethod.GET)
    public ResponseEntity<Deal> details(@PathVariable long transactionId) {
        Deal deal = realEstateService.findTransactionDetails(transactionId);
        return deal != null ? ResponseEntity.ok(deal) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
