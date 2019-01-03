package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.LandTaxZone;

import java.util.List;

public interface LandTaxZoneService {

    List<LandTaxZone> fetchAllZones();
}
