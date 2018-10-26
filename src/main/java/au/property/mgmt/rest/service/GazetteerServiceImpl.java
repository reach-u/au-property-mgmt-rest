package au.property.mgmt.rest.service;

import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author taaviv @ 26.10.18
 */
@Service
public class GazetteerServiceImpl implements GazetteerService {

    private final Client client;

    @Autowired
    public GazetteerServiceImpl(Client client) {
        this.client = client;
    }

    @Override
    public List<String> getCountries() {
        List<String> results = Lists.newArrayList();
        SearchResponse sr = client.prepareSearch("address")
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders
                        .terms("countries")
                        .field("collector.country")
                        .size(10_000))
                .setSize(10_000)
                .execute().actionGet(TimeValue.timeValueSeconds(9000));
        Terms t = sr.getAggregations().get("countries");
        for (Terms.Bucket bucket : t.getBuckets()) {
            results.add(bucket.getKey().toString());
        }
        return results;
    }

    @Override
    public List<String> getCounties(String country) {
        List<String> results = Lists.newArrayList();
        SearchResponse sr = client.prepareSearch("address")
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery("collector.country", country))
                .addAggregation(AggregationBuilders
                        .terms("countries")
                        .field("collector.county")
                        .size(10_000))
                .setSize(10_000)
                .execute().actionGet(TimeValue.timeValueSeconds(9000));
        Terms t = sr.getAggregations().get("countries");
        for (Terms.Bucket bucket : t.getBuckets()) {
            results.add(bucket.getKey().toString());
        }
        return results;
    }

}
