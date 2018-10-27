package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Deal;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * @author taaviv @ 27.10.18
 */
@Service
@Slf4j
public class RealEstateServiceImpl implements RealEstateService {

    private static final AtomicLong COUNTER = new AtomicLong();

    private final Cache<Long, Deal> dealCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).build();

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
        return sign(transactionId, Deal::signByBuyer);
    }


    @Override
    public Deal signBySeller(long transactionId) {
        return sign(transactionId, Deal::signBySeller);
    }

    private Deal sign(long transactionId, Consumer<Deal> sign) {
        log.info("sign by buyer: transaction={}", transactionId);
        Deal deal = dealCache.getIfPresent(transactionId);
        if (deal != null) {
            // TODO check payment
            sign.accept(deal);
            if (deal.isSignedByAll()) {
                deal.setAddress(addressService.changeOwner(deal.getAddress(), deal.getBuyerIdCode()));
            }
        }

        return deal;
    }

}