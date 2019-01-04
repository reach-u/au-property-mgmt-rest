package au.property.mgmt.rest.service.converters;

import au.property.mgmt.rest.model.LandTaxZone;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ZoneConverter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Collection<LandTaxZone> convert(SearchResponse searchResponse) {
        return Arrays.stream(searchResponse.getHits().getHits())
                .map(ZoneConverter::convert).collect(Collectors.toList());
    }

    private static LandTaxZone convert(SearchHit hit) {
        try {
            return OBJECT_MAPPER.readValue(hit.getSourceAsString(), LandTaxZone.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
