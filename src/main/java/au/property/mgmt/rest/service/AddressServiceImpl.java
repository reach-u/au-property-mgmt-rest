package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Coordinate;
import org.springframework.stereotype.Service;

/**
 * @author taaviv @ 26.10.18
 */
@Service
public class AddressServiceImpl implements AddressService {

    private static final Address DUMMY = Address.builder()
            .id(10432)
            .country("Kenya")
            .county("Mombasa")
            .city("Mombasa")
            .street("Magongo rd")
            .house("Alkheral Bakery")
            .coordinate(new Coordinate(39.663940, -4.057460))
            .build();

    @Override
    public Address[] search(String query) {
        return new Address[] {
                DUMMY
        };
    }

    @Override
    public Address search(long id) {
        return DUMMY;
    }

}
