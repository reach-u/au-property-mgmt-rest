package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Deal;
import org.springframework.stereotype.Service;

/**
 * @author taaviv @ 27.10.18
 */
@Service
public class TaxPaymentServiceImpl implements TaxPaymentService {

    private static final double AMOUNT = 20.0;
    private static final String CURRENCY = "USD";
    private static final long REFERENCE_NUMBER = 1213129552L;

    @Override
    public boolean pay(Deal deal) {
        deal.setPaid(true);
        return true;
    }
}
