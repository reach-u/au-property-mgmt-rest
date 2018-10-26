package au.property.mgmt.rest.service;

import java.util.List;

public interface GazetteerService {

    List<String> getCountries();

    List<String> getCounties(String country);

}
