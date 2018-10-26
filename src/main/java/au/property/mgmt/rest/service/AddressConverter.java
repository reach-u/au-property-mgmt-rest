package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * @author taaviv @ 26.10.18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressConverter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        OBJECT_MAPPER.setDateFormat(sdf);
    }

    public static Collection<Address> convert(SearchResponse searchResponse) {
        return Arrays.stream(searchResponse.getHits().getHits())
                .map(AddressConverter::convertAddress).collect(Collectors.toList());
    }

    private static Address convertAddress(SearchHit hit) {
        try {
            return OBJECT_MAPPER.readValue(hit.getSourceAsString(), Address.class);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
