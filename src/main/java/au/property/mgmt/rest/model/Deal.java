package au.property.mgmt.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author taaviv @ 27.10.18
 */
@Data
public class Deal {

    private long transactionId;

    private long buyerIdCode;

    private Address address;

    private Date signedByBuyer;

    private Date signedBySeller;

    private boolean paid;

    private final Date startedDate;

    public Deal(long transactionId, long buyerIdCode, Address address) {
        this.transactionId = transactionId;
        this.buyerIdCode = buyerIdCode;
        this.address = address;
        startedDate = new Date();
    }

    public void signByBuyer() {
        signedByBuyer = new Date();
    }

    public void signBySeller() {
        signedBySeller = new Date();
    }

    public boolean isSignedByAll() {
        return signedByBuyer != null && signedBySeller != null;
    }

}
