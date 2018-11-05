package au.property.mgmt.rest.controller.priv;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Deal;
import au.property.mgmt.rest.service.AddressService;
import au.property.mgmt.rest.service.RealEstateService;
import au.property.mgmt.rest.service.TaxPaymentService;
import au.property.mgmt.rest.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author taaviv @ 26.10.18
 */
@RestController
@RequestMapping(Constants.PRIVATE_API_V1_URL + "/re")
public class RealEstateController {

    private final RealEstateService realEstateService;

    private final AddressService addressService;

    private final TaxPaymentService paymentService;

    public RealEstateController(RealEstateService realEstateService, AddressService addressService,
                                TaxPaymentService paymentService) {
        this.realEstateService = realEstateService;
        this.addressService = addressService;
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "buy/{buyerIdCode}/{addressId}", method = RequestMethod.POST)
    public ResponseEntity<Deal> buy(@PathVariable long buyerIdCode, @PathVariable long addressId) {
        Address address = addressService.search(addressId);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(realEstateService.buy(buyerIdCode, address));
    }

    @RequestMapping(value = "pay_tax/{transactionId}", method = RequestMethod.POST)
    public ResponseEntity pay(@PathVariable long transactionId) {
        Deal deal = realEstateService.findTransactionDetails(transactionId);
        if (deal == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        boolean success = paymentService.pay(deal);
        return success ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(value = "details_by_address/{addressId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Deal>> detailsByAddress(@PathVariable long addressId) {
        return ResponseEntity.ok(realEstateService.findTransactionDetailsByAddress(addressId));
    }

    @RequestMapping(value = "details_by_person/{personId}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Deal>> detailsByPerson(@PathVariable long personId) {
        return ResponseEntity.ok(realEstateService.findTransactionDetailsByPerson(personId));
    }

}
