package au.property.mgmt.rest.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author taaviv @ 26.10.18
 */
@Data
@Builder
public class GazetteerRequest {

    private String country;

    private String county;

    private String city;

    private String street;

    private String house;

}
