package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Deal;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author taaviv @ 27.10.18
 */
@Service
@Slf4j
public class RealEstateServiceImpl implements RealEstateService {

    private static final AtomicLong COUNTER = new AtomicLong();

    private final Cache<Long, Deal> dealCache = CacheBuilder.newBuilder().expireAfterAccess(24, TimeUnit.HOURS).build();

    private final AddressService addressService;

    @Autowired
    public RealEstateServiceImpl(AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public Deal buy(long buyerIdCode, Address address) {
        log.info("buy: buyer={}, {}", buyerIdCode, address);
        Deal deal = new Deal(COUNTER.incrementAndGet(), buyerIdCode, address);
        dealCache.put(deal.getTransactionId(), deal);
        return deal;
    }

    @Override
    public Deal signByBuyer(long transactionId) {
        log.info("sign by buyer: transaction={}", transactionId);
        return sign(transactionId, Deal::signByBuyer);
    }


    @Override
    public Deal signBySeller(long transactionId) {
        log.info("sign by seller: transaction={}", transactionId);
        return sign(transactionId, Deal::signBySeller);
    }

    @Override
    public Deal findTransactionDetails(long transactionId) {
        log.info("find transaction details: id={}", transactionId);
        return dealCache.getIfPresent(transactionId);
    }

    @Override
    public Collection<Deal> findTransactionDetailsByAddress(long addressId) {
        log.info("find transaction details: address id={}", addressId);
        return dealCache.asMap().values().stream()
                .filter(deal -> deal.getAddress().getId() == addressId)
                .sorted(Comparator.comparing(Deal::getStartedDate)).collect(Collectors.toList());
    }

    @Override
    public Collection<Deal> findTransactionDetailsByPerson(long personId) {
        log.info("find transaction details: person id={}", personId);
        return dealCache.asMap().values().stream()
                .filter(deal -> deal.getBuyerIdCode() == personId || deal.getSellerIdCode() == personId)
                .sorted(Comparator.comparing(Deal::getStartedDate)).collect(Collectors.toList());
    }

    private Deal sign(long transactionId, Consumer<Deal> sign) {
        Deal deal = dealCache.getIfPresent(transactionId);
        if (deal != null) {
            if (!deal.isPaid()) {
                throw new IllegalStateException("State tax is not paid");
            }
            sign.accept(deal);
            if (deal.isSignedByAll()) {
                deal.setAddress(addressService.changeOwner(deal.getAddress(), deal.getBuyerIdCode()));
            }
        }

        return deal;
    }

}
