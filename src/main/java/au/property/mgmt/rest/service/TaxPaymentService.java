package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Deal;

/**
 * @author taaviv @ 27.10.18
 */
public interface TaxPaymentService {

    /**
     * Pay state tax
     *
     * @param deal
     * @return true if payment was successful
     */
    boolean pay(Deal deal);

}
