package au.property.mgmt.rest.service;

import au.property.mgmt.rest.elasticsearch.ElasticPersister;
import au.property.mgmt.rest.elasticsearch.ElasticSearcher;
import au.property.mgmt.rest.elasticsearch.Indices;
import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.LandTaxPayment;
import au.property.mgmt.rest.service.converters.PaymentConverter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class LandTaxPaymentServiceImpl implements LandTaxPaymentService {

    @Autowired
    private ElasticPersister persister;

    @Autowired
    private ElasticSearcher searcher;

    @Autowired
    private AddressService addressService;

    public LandTaxPayment pay(long id) throws InstanceNotFoundException {
        log.info("Paying land tax with id={]", id);
        LandTaxPayment landTaxPayment = fetchLandTaxPayment(id)
                .orElseThrow(() -> new InstanceNotFoundException("Land tax payment not found"));
        landTaxPayment.setPaid(true);
        persister.saveTaxPayment(landTaxPayment, Indices.payment());
        return landTaxPayment;
    }

    @Override
    @Scheduled(cron = "0 0 1 1 * ?")
    public void generatePayments() {
        log.info("Monthly payment generation has been started");

        Collection<LandTaxPayment> payments = PaymentConverter.convert(searcher.searchMaxId(
                QueryBuilders.matchAllQuery(), Indices.payment(), 1));

        long newId = payments.size() != 0 ? payments.iterator().next().getId() + 1 : 0;

        for (Address address : addressService.findAll()) {
            createPayment(address, newId);
            newId = newId + 1;
        }
    }

    @Override
    public Optional<LandTaxPayment> fetchLandTaxPayment(long id) {
        log.info("search: id={}", id);
        QueryBuilder builder = QueryBuilders.idsQuery().addIds(id + "");
        Collection<LandTaxPayment> payments = PaymentConverter.convert(searcher.search(builder, Indices.payment(), 1));
        return Optional.of(payments.iterator().next());
    }

    private void createPayment(Address address, long id) {
        LandTaxPayment taxPayment = LandTaxPayment.builder()
                .id(id)
                .address(address)
                .dueDate(calculateDueDate())
                .ownerIdCode(address.getDetailedData().getCurrentOwner())
                .payable(address.getDetailedData().getLandTaxValue()).build();
        persister.saveTaxPayment(taxPayment, Indices.payment());
    }

    private Date calculateDueDate() {
        LocalDate dueDate = LocalDate.now().plusMonths(1);
        return Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
