package au.property.mgmt.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author taaviv @ 26.10.18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {

    private double lon;

    private double lat;

}
