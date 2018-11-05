package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Deal;

import java.util.Collection;

/**
 * @author taaviv @ 27.10.18
 */
public interface RealEstateService {

    Deal buy(long buyerIdCode, Address address);

    Deal signByBuyer(long transactionId);

    Deal signBySeller(long transactionId);

    Deal findTransactionDetails(long transactionId);

    Collection<Deal> findTransactionDetailsByAddress(long addressId);

    Collection<Deal> findTransactionDetailsByPerson(long personId);

}
