package au.property.mgmt.rest.elasticsearch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

/**
 * @author taaviv @ 26.10.18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Indices {

    public static Supplier<String> address() {
        return () -> "address";
    }

}
