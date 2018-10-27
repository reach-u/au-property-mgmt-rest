package au.property.mgmt.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author taaviv @ 27.10.18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Deal {

    private long transactionId;

    private long buyerIdCode;

    private Address address;

}
