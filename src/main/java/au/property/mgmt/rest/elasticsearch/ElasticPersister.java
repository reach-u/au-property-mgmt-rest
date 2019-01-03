package au.property.mgmt.rest.elasticsearch;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.LandTaxPayment;

import java.util.function.Supplier;

/**
 * @author taaviv @ 27.10.18
 */
public interface ElasticPersister {

    void save(Address address, Supplier<String> index);

    void saveTaxPayment(LandTaxPayment taxPayment, Supplier<String> index);

}
