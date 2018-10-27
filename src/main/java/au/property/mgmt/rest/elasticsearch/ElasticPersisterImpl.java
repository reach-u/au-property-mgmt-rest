package au.property.mgmt.rest.elasticsearch;

import au.property.mgmt.rest.model.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * @author taaviv @ 27.10.18
 */
@Service
@Slf4j
public class ElasticPersisterImpl implements ElasticPersister {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Client client;

    static {
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public ElasticPersisterImpl(Client client) {
        this.client = client;
    }

    @Override
    public void save(Address address, Supplier<String> index) {
        log.debug("save: {}", address);
        try {
            client.prepareIndex(index.get(), Address.class.getSimpleName().toLowerCase(), address.getId() + "")
                    .setSource(OBJECT_MAPPER.writeValueAsString(address), XContentType.JSON).execute().actionGet();
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
