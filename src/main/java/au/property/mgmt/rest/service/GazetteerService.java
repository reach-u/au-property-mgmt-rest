package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.GazetteerRequest;

import java.util.Collection;

public interface GazetteerService {

    Collection<String> getCountries();

    Collection<String> getCounties(GazetteerRequest req);

    Collection<String> getCities(GazetteerRequest req);

    Collection<String> getStreets(GazetteerRequest req);

    Collection<String> getHouses(GazetteerRequest req);

    Collection<Address> getAddresses(GazetteerRequest req);

}
