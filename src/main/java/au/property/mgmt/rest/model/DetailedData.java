package au.property.mgmt.rest.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author taaviv @ 26.10.18
 */
@Data
public class DetailedData {

    private String propertyType;

    private int propertySize;

    private String propertyBordersGeometry;

    private String landNumber;

    private String landBordersGeometry;

    private int landAcreage;

    private String landType1;

    private Date landMeasurementDate;

    private String surveyor;

    private String landLimitations1;

    private String buildingType;

    private String buildingStatus;

    private Date certificateOfOccupancy;

    private String areaUnderBuilding;

    private String numberOfFloors;

    private int height;

    private String waterSupply;

    private String sewerType;

    private Date lastOwnerChangeDate;

    private Long previousOwner;

    private Long currentOwner;

    private Integer mortgageSize;

    private Long mortgageSubject;

    private Double[][] cadastre;

    private LandTaxZone taxZone;

    private BigDecimal landTaxValue;

}
