package au.property.mgmt.rest.controller;

import au.property.mgmt.rest.service.GazetteerService;
import au.property.mgmt.rest.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<String>> getCountries() {
        List<String> countries = gazetteerService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @RequestMapping(value = "gzt/{country}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getCounties(@PathVariable String country) {
        List<String> counties = gazetteerService.getCounties(country);
        return ResponseEntity.ok(counties);
    }

}
