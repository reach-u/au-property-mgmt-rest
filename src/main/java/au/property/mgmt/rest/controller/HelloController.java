package au.property.mgmt.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taaviv @ 25.10.18
 */
@RestController
@RequestMapping("api/1")
public class HelloController {

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello!";
    }

}
