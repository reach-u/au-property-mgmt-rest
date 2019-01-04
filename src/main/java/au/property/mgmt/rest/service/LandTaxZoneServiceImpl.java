package au.property.mgmt.rest.service;

import au.property.mgmt.rest.elasticsearch.ElasticSearcher;
import au.property.mgmt.rest.elasticsearch.Indices;
import au.property.mgmt.rest.model.LandTaxZone;
import au.property.mgmt.rest.service.converters.ZoneConverter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LandTaxZoneServiceImpl implements LandTaxZoneService {

    @Autowired
    private ElasticSearcher searcher;

    private static final int LIMIT = 100;

    @Override
    public LandTaxZone[] getAllZones() {
        log.info("Find all tax zones");
        return ZoneConverter.convert(searcher.search(
                QueryBuilders.matchAllQuery(), Indices.zone(), LIMIT)).toArray(new LandTaxZone[0]);
    }
}
