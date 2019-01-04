package au.property.mgmt.rest.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * @author taaviv @ 26.10.18
 */
@Service
public class ElasticSearcherImpl implements ElasticSearcher {

    private final Client client;

    private int timeout = 10;

    @Autowired
    public ElasticSearcherImpl(Client client) {
        this.client = client;
    }


    @Override
    public SearchResponse search(QueryBuilder queryBuilder, Supplier<String> index, int limit) {
        return client.prepareSearch(index.get())
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setSize(limit)
                .execute().actionGet(TimeValue.timeValueSeconds(timeout));
    }

    @Override
    public SearchResponse searchMaxId(QueryBuilder queryBuilder, Supplier<String> index, int limit) {
        return client.prepareSearch(index.get())
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .setSize(limit)
                .addSort("id", SortOrder.DESC)
                .execute().actionGet(TimeValue.timeValueSeconds(timeout));
    }
}
