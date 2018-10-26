package au.property.mgmt.rest.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author taaviv @ 26.10.18
 */
@Data
@Builder
public class Address {

    private long id;

    private String country;

    private String county;

    private String city;

    private String street;

    private String house;

    private String apartment;

    private Coordinate coordinates;

    private String streetuUrl;

    private String name;

    private String comment;

    private DetailedData detailedData;

}
