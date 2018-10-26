package au.property.mgmt.rest.service;

import au.property.mgmt.rest.elasticsearch.ElasticSearcher;
import au.property.mgmt.rest.elasticsearch.Indices;
import au.property.mgmt.rest.model.Address;
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

    private static final int LIMIT = 100;

    private final ElasticSearcher searcher;

    @Autowired
    public AddressServiceImpl(ElasticSearcher searcher) {
        this.searcher = searcher;
    }

    @Override
    public Address[] search(String query) {
        return AddressConverter.convert(searcher.search(
                ElasticQueryBuilder.createGeocodeQuery(query), Indices.address(), LIMIT)).toArray(new Address[0]);
    }

    @Override
    public Address search(long id) {
        QueryBuilder builder = QueryBuilders.idsQuery().addIds(id + "");
        Collection<Address> addresses = AddressConverter.convert(searcher.search(builder, Indices.address(), 1));
        return addresses.isEmpty() ? null : addresses.iterator().next();
    }

}
