package au.property.mgmt.rest.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LandTaxZone {
    private String name;
    private Double[][] zoneCoordinates;
    private BigDecimal squareMeterPrice;
}
