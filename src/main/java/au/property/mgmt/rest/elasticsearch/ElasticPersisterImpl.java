package au.property.mgmt.rest.elasticsearch;

import au.property.mgmt.rest.model.Address;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * @author taaviv @ 27.10.18
 */
@Service
@Slf4j
public class ElasticPersisterImpl implements ElasticPersister {

    private final Client client;

    public ElasticPersisterImpl(Client client) {
        this.client = client;
    }

    @Override
    public void save(Address address, Supplier<String> index) {
        log.debug("save: {}", address);
        client.prepareUpdate(index.get(), Address.class.getSimpleName().toLowerCase(), address.getId() + "")
                .setDoc(address).get();
    }

}
