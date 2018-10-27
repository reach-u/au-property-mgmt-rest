package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;

/**
 * @author taaviv @ 26.10.18
 */
public interface AddressService {

    Address[] search(String query);

    Address[] findAll();

    Address search(long id);

    Address changeOwner(Address address, long newOwnerIdCode);

}
