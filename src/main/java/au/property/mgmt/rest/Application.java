package au.property.mgmt.rest;

import au.property.mgmt.rest.conf.Conf;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author taaviv @ 25.10.18
 */
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Conf.class).run(args);
    }

}
