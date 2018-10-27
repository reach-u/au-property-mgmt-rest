package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Address;
import au.property.mgmt.rest.model.Deal;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author taaviv @ 27.10.18
 */
@Service
@Slf4j
public class RealEstateServiceImpl implements RealEstateService {

    private static final AtomicLong COUNTER = new AtomicLong();

    private final Cache<Long, Deal> dealCache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).build();

    @Override
    public Deal buy(long buyerIdCode, Address address) {
        log.info("Buy: buyer={}, {}", buyerIdCode, address);
        Deal deal = new Deal(COUNTER.incrementAndGet(), buyerIdCode, address);
        dealCache.put(deal.getTransactionId(), deal);
        return deal;
    }

}
