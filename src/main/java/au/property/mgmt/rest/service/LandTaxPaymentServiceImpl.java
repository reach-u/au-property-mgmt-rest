package au.property.mgmt.rest.service;

import au.property.mgmt.rest.elasticsearch.ElasticPersister;
import au.property.mgmt.rest.elasticsearch.ElasticSearcher;
import au.property.mgmt.rest.elasticsearch.Indices;
import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.DTO.MonthlyTaxStatsDTO;
import au.property.mgmt.rest.model.DTO.TaxStatsDTO;
import au.property.mgmt.rest.model.DTO.UserPaymentsDTO;
import au.property.mgmt.rest.model.LandTaxPayment;
import au.property.mgmt.rest.service.converters.PaymentConverter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    @Scheduled(cron = "0 30 3 * * ?")
    public void generatePayments() {
        log.info("Monthly payment generation has been started");

        long newId = 439;
        LocalDate beginDate = LocalDate.of(2019, Month.JANUARY, 1);
        Period diff = Period.between(beginDate, LocalDate.now());

        for (int i = 0; i < diff.getMonths(); i++) {
            LocalDate dueDate = beginDate.plusMonths(i + 1);
            for (Address address : addressService.findAll()) {
                createPayment(address, newId, dueDate);
                newId = newId + 1;
            }
        }
    }

    @Override
    public List<UserPaymentsDTO> getPaymentsByUser(long userId) {
        log.info("find payments for person id={}", userId);
        Map<String, List<LandTaxPayment>> userPaymentsByMonth = Arrays.stream(getAllPayments())
                .filter(payment -> payment.getOwnerIdCode() == userId)
                .collect(Collectors.groupingBy(payment -> getYearMonthNameFromDate(payment.getDueDate())));

        return userPaymentsByMonth.entrySet()
                .stream()
                .map(entry -> new UserPaymentsDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.nullsLast(Comparator.comparing(e -> e.getPayments().get(0).getDueDate())))
                .collect(Collectors.toList());
    }

    @Override
    public LandTaxPayment[] getAllPayments() {
        log.info("Find all payments");
        return PaymentConverter.convert(searcher.search(
                QueryBuilders.matchAllQuery(), Indices.payment(), 1000)).toArray(new LandTaxPayment[0]);
    }

    @Override
    public List<TaxStatsDTO> getAreaStatistics() {
        Map<String, List<LandTaxPayment>> paymentsGroupedByZoneName = Arrays.stream(getAllPayments())
                .filter(payment -> paymentIsFromYear(payment, 2018))
                .collect(Collectors.groupingBy(this::getPaymentZoneName));
        return paymentsGroupedByZoneName.entrySet()
                .stream()
                .map(entry -> new TaxStatsDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaxStatsDTO> getMonthlyStatistics() {
        Map<String, List<LandTaxPayment>> paymentsGroupedByMonth = Arrays.stream(getAllPayments())
                .collect(Collectors.groupingBy(payment -> getYearMonthNameFromDate(payment.getDueDate())));
        return paymentsGroupedByMonth.entrySet()
                .stream()
                .map(entry -> new MonthlyTaxStatsDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.nullsLast(Comparator.comparing(e -> getYearMonthFromString(e.getName()))))
                .collect(Collectors.toList());
    }

    private Optional<LandTaxPayment> fetchLandTaxPayment(long id) {
        log.info("search: id={}", id);
        QueryBuilder builder = QueryBuilders.idsQuery().addIds(id + "");
        Collection<LandTaxPayment> payments = PaymentConverter.convert(searcher.search(builder, Indices.payment(), 1));
        return Optional.of(payments.iterator().next());
    }

    private void createPayment(Address address, long id, LocalDate dueDate) {
        LandTaxPayment taxPayment = LandTaxPayment.builder()
                .id(id)
                .address(address)
                .dueDate(localDateToDate(dueDate))
                .ownerIdCode(address.getDetailedData().getCurrentOwner())
                .payable(address.getDetailedData().getLandTaxValue()).build();
        persister.saveTaxPayment(taxPayment, Indices.payment());
    }

    private Date localDateToDate(LocalDate dueDate) {
        return Date.from(dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private String getPaymentZoneName(LandTaxPayment payment) {
        return payment.getAddress().getDetailedData().getTaxZone().getName();
    }

    private String getYearMonthNameFromDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        YearMonth yearMonth = YearMonth.from(localDate);
        return yearMonth.format(getYearMonthDateTimeFromatter());
    }

    private YearMonth getYearMonthFromString(String yearMonthFormattedString) {
        return YearMonth.parse(yearMonthFormattedString, getYearMonthDateTimeFromatter());
    }

    private DateTimeFormatter getYearMonthDateTimeFromatter() {
        return DateTimeFormatter.ofPattern(
                "MMM uuuu",
                Locale.ENGLISH
        );
    }

    private boolean paymentIsFromYear(LandTaxPayment landTaxPayment, int yearNumber) {
        Instant beginOfYear = LocalDateTime.of(yearNumber, Month.JANUARY, 1, 0, 0).atZone(ZoneId.systemDefault()).toInstant();
        Instant endOfYear = LocalDateTime.of(yearNumber, Month.DECEMBER, 31, 23, 59).atZone(ZoneId.systemDefault()).toInstant();
        Instant paymentInstant = landTaxPayment.getDueDate().toInstant();
        return paymentInstant.isAfter(beginOfYear) && paymentInstant.isBefore(endOfYear);
    }
}
