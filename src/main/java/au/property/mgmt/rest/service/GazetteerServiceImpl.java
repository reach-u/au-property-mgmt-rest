package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.GazetteerRequest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author taaviv @ 26.10.18
 */
@Service
@Slf4j
public class GazetteerServiceImpl implements GazetteerService {

    private final Client client;

    @Autowired
    public GazetteerServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public Collection<String> getCountries() {
        log.info("get countries");
        return extractAggregations(search(QueryBuilders.matchAllQuery(), "collector.country"));
    }

    @Override
    public Collection<String> getCounties(GazetteerRequest req) {
        log.info("get counties: {}", req);
        return extractAggregations(search(queryBuilder(req), "collector.county"));
    }

    @Override
    public Collection<String> getCities(GazetteerRequest req) {
        log.info("get cities: {}", req);
        return extractAggregations(search(queryBuilder(req), "collector.city"));
    }

    @Override
    public Collection<String> getStreets(GazetteerRequest req) {
        log.info("get streets: {}", req);
        return extractAggregations(search(queryBuilder(req), "collector.street"));
    }

    @Override
    public Collection<String> getHouses(GazetteerRequest req) {
        log.info("get houses: {}", req);
        return extractAggregations(search(queryBuilder(req), "collector.house"));
    }

    @Override
    public Collection<Address> getAddresses(GazetteerRequest req) {
        log.info("get addresses: {}", req);
        return AddressConverter.convert(
                client.prepareSearch("address")
                        .setSearchType(SearchType.QUERY_THEN_FETCH)
                        .setQuery(queryBuilder(req))
                        .setSize(10_000)
                        .execute().actionGet(TimeValue.timeValueSeconds(9000))
        );
    }

    private BoolQueryBuilder queryBuilder(GazetteerRequest req) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (req.getCountry() != null) {
            boolQuery.must(QueryBuilders.matchQuery("collector.country", req.getCountry()).operator(Operator.AND));
        }

        if (req.getCounty() != null) {
            boolQuery.must(QueryBuilders.matchQuery("collector.county", req.getCounty()).operator(Operator.AND));
        }

        if (req.getCity() != null) {
            boolQuery.must(QueryBuilders.matchQuery("collector.city", req.getCity()).operator(Operator.AND));
        }

        if (req.getStreet() != null) {
            boolQuery.must(QueryBuilders.matchQuery("collector.street", req.getStreet()).operator(Operator.AND));
        }

        if (req.getHouse() != null) {
            boolQuery.must(QueryBuilders.matchQuery("collector.house", req.getHouse()));
        }

        return boolQuery;
    }

    private List<String> extractAggregations(SearchResponse sr) {
        Terms terms = sr.getAggregations().get("terms");
        return terms.getBuckets().stream().map(bucket -> bucket.getKey().toString()).collect(Collectors.toList());
    }

    private SearchResponse search(QueryBuilder query, String field) {
        return client.prepareSearch("address")
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(query)
                .addAggregation(AggregationBuilders
                        .terms("terms")
                        .field(field)
                        .size(10_000))
                .setSize(10_000)
                .execute().actionGet(TimeValue.timeValueSeconds(9000));
    }

}
