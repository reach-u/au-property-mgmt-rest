package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Deal;

/**
 * @author taaviv @ 27.10.18
 */
public interface RealEstateService {

    Deal buy(long buyerIdCode, Address address);

}
