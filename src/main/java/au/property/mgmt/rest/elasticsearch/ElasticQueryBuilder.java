package au.property.mgmt.rest.elasticsearch;

import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;

/**
 * @author taaviv @ 26.10.18
 */
public class ElasticQueryBuilder {

    private static final String CONTAINS_NUMBER_PATTERN = ".*\\d+.*";

    public static QueryBuilder createGeocodeQuery(String query) {
        BoolQueryBuilder mQueryBuilderForTopLevelFilter = QueryBuilders.boolQuery()
                .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("house")))
                .should(QueryBuilders.matchQuery("house", query).analyzer("standard"));

        BoolQueryBuilder builder = QueryBuilders.boolQuery().must(createQuery(query));

        if (query.matches(CONTAINS_NUMBER_PATTERN)) {
            builder = builder.filter(mQueryBuilderForTopLevelFilter);
        }

        return builder;
    }

    private static FunctionScoreQueryBuilder createQuery(String query) {
        MatchQueryBuilder defaultMatchQueryBuilder =
                QueryBuilders.matchQuery("collector.default", query)
                        .fuzziness(Fuzziness.ZERO)
                        .prefixLength(2)
                        .analyzer("search_ngram")
                        .minimumShouldMatch("100%");
        return new FunctionScoreQueryBuilder(defaultMatchQueryBuilder).boostMode(CombineFunction.MULTIPLY);
    }

}
