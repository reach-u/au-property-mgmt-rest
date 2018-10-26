package au.property.mgmt.rest.controller;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.service.AddressService;
import au.property.mgmt.rest.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author taaviv @ 26.10.18
 */
@RestController
@RequestMapping(Constants.API_V1_URL)
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "address", method = RequestMethod.GET)
    public ResponseEntity<Address[]> findAddress(@RequestParam("q") String query) {
        return ResponseEntity.ok(addressService.search(query));
    }

    @RequestMapping(value = "address/{id}", method = RequestMethod.GET)
    public ResponseEntity<Address> findAddress(@PathVariable("id") long id) {
        Address address = addressService.search(id);
        return address != null ? ResponseEntity.ok(address) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "distance/{id1}/{id2}", method = RequestMethod.GET)
    public ResponseEntity<Integer> calculateDistance(@PathVariable("id1") long id1, @PathVariable("id2") long id2) {
        return ResponseEntity.ok(new Random().nextInt(25_000));
    }

}
