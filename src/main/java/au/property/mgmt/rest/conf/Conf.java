package au.property.mgmt.rest.conf;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author taaviv @ 25.10.18
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = { "au.property.mgmt.rest" })
public class Conf {

}
