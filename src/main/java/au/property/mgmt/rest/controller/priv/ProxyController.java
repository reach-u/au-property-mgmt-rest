package au.property.mgmt.rest.controller.priv;

import au.property.mgmt.rest.util.Constants;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author taaviv @ 31.10.18
 */
@RestController
@RequestMapping(Constants.PRIVATE_API_V1_URL + "/proxy")
@Slf4j
public class ProxyController {

    private static final String URL = "https://egov-demo-ss3.westeurope.cloudapp.azure.com/restapi/GOV/M-LAND/RE-REG" +
            "?xRoadInstance=EGOV-EXAMPLE&memberClass=GOV&memberCode=M-HOMEAFFAIRS&subsystemCode=POP-REG" +
            "&serviceCode=persons&serviceVersion=1&dateFrom=1900-01-01T00:00:00.440Z&dateTo=2018-10-30T23:59:59.440Z";

    private final OkHttpClient client;

    @Autowired
    public ProxyController(OkHttpClient client) {
        this.client = client;
    }

    @RequestMapping(value = "persons", method = RequestMethod.GET)
    public ResponseEntity<String> getPersons() {
        Request request = new Request.Builder().url(URL).build();

        Response response;
        try {
            response = client.newCall(request).execute();
        }
        catch (IOException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            return ResponseEntity.ok(responseBody.string());
        }
        catch (IOException e) {
            log.error("", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
