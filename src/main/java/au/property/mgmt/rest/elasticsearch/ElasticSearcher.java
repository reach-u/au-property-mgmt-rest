package au.property.mgmt.rest.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.Supplier;

/**
 * @author taaviv @ 26.10.18
 */
public interface ElasticSearcher {

    SearchResponse search(QueryBuilder queryBuilder, Supplier<String> index, int limit);

    SearchResponse searchMaxId(QueryBuilder queryBuilder, Supplier<String> index, int limit);

}
