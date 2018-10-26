package au.property.mgmt.rest.controller;

import au.property.mgmt.rest.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taaviv @ 25.10.18
 */
@RestController
@RequestMapping(Constants.API_V1_URL)
@Slf4j
public class HelloController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String sayHello() {
        log.debug("Somebody said hello");
        return "Hello!";
    }

}
