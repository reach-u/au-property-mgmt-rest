package au.property.mgmt.rest.controller.pub;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.GazetteerRequest;
import au.property.mgmt.rest.service.GazetteerService;
import au.property.mgmt.rest.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(Constants.API_V1_URL)
public class GazetterController {

    private final GazetteerService gazetteerService;

    @Autowired
    public GazetterController(GazetteerService gazetteerService) {
        this.gazetteerService = gazetteerService;
    }

    @RequestMapping(value = "gzt", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getCountries() {
        return ResponseEntity.ok(gazetteerService.getCountries());
    }

    @RequestMapping(value = "gzt/{country}", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getCounties(@PathVariable String country) {
        return ResponseEntity.ok(gazetteerService.getCounties(GazetteerRequest.builder().country(country).build()));
    }

    @RequestMapping(value = "gzt/{country}/{county}", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getCities(@PathVariable String country, @PathVariable String county) {
        return ResponseEntity.ok(gazetteerService.getCities(
                GazetteerRequest.builder()
                        .country(country)
                        .county(county)
                        .build()
        ));
    }

    @RequestMapping(value = "gzt/{country}/{county}/{city}", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getStreets(@PathVariable String country, @PathVariable String county,
                                                         @PathVariable String city) {
        return ResponseEntity.ok(gazetteerService.getStreets(
                GazetteerRequest.builder()
                        .country(country)
                        .county(county)
                        .city(city)
                        .build()
        ));
    }

    @RequestMapping(value = "gzt/{country}/{county}/{city}/{street}", method = RequestMethod.GET)
    public ResponseEntity<Collection<String>> getHouses(@PathVariable String country, @PathVariable String county,
                                                        @PathVariable String city, @PathVariable String street) {
        return ResponseEntity.ok(gazetteerService.getHouses(
                GazetteerRequest.builder()
                        .country(country)
                        .county(county)
                        .city(city)
                        .street(street)
                        .build()
        ));
    }

    @RequestMapping(value = "gzt/{country}/{county}/{city}/{street}/{house}", method = RequestMethod.GET)
    public ResponseEntity<Collection<Address>> getAddresses(@PathVariable String country, @PathVariable String county,
                                                            @PathVariable String city, @PathVariable String street,
                                                            @PathVariable String house) {
        return ResponseEntity.ok(gazetteerService.getAddresses(
                GazetteerRequest.builder()
                        .country(country)
                        .county(county)
                        .city(city)
                        .street(street)
                        .house(house)
                        .build()
        ));
    }

}
