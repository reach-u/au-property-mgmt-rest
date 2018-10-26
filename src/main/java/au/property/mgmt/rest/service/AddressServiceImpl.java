package au.property.mgmt.rest.service;

import au.property.mgmt.rest.elasticsearch.ElasticSearcher;
import au.property.mgmt.rest.elasticsearch.Indices;
import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Coordinate;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
            .coordinates(new Coordinate(39.663940, -4.057460))
            .build();

    private final ElasticSearcher searcher;

    @Autowired
    public AddressServiceImpl(ElasticSearcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public Address[] search(String query) {
        return new Address[] {
                DUMMY
        };
    }

    @Override
    public Address search(long id) {
        QueryBuilder builder = QueryBuilders.idsQuery().addIds(id + "");
        Collection<Address> addresses = AddressConverter.convert(searcher.search(builder, Indices.address(), 1));
        return addresses.isEmpty() ? null : addresses.iterator().next();
    }

}
