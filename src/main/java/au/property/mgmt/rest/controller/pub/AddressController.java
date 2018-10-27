package au.property.mgmt.rest.controller.pub;

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

import java.util.Arrays;

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
        Address address1 = addressService.search(id1);
        Address address2 = addressService.search(id2);

        if (address1 == null || address2 == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(distance(
                address1.getCoordinates().getLat(), address2.getCoordinates().getLat(),
                address1.getCoordinates().getLon(), address2.getCoordinates().getLon()
        ));
    }

    @RequestMapping(value = "find_by_owner/{ownerIdCode}", method = RequestMethod.GET)
    public ResponseEntity<Address[]> findByOwner(@PathVariable long ownerIdCode) {
        return ResponseEntity.ok(Arrays.stream(addressService.findAll())
                .filter(address -> address.getDetailedData().getCurrentOwner() == ownerIdCode).toArray(Address[]::new));
    }

    private static int distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (int) (R * c * 1000);
    }

}
