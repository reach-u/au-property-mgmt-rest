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
@RequestMapping(Constants.PRIVATE_API_V1_URL)
public class RealEstateController {

    private static final long[] DUMMY_BUYERS = new long[] { 70101010000L, 80101010001L };

    private final RealEstateService realEstateService;

    private final AddressService addressService;

    public RealEstateController(RealEstateService realEstateService, AddressService addressService) {
        this.realEstateService = realEstateService;
        this.addressService = addressService;
    }

    @RequestMapping(value = "buy/{addressId}", method = RequestMethod.GET)
    public ResponseEntity<Deal> buy(@PathVariable int addressId) {
        Address address = addressService.search(addressId);
        if (address == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        long buyerIdCode = DUMMY_BUYERS[0] == address.getDetailedData().getCurrentOwner() ?
                DUMMY_BUYERS[1] : DUMMY_BUYERS[0];

        return ResponseEntity.ok(realEstateService.buy(buyerIdCode, address));
    }

}
