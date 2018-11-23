package au.property.mgmt.rest.service;

import au.property.mgmt.rest.model.Deal;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author taaviv @ 27.10.18
 */
@Service
@Slf4j
public class TaxPaymentServiceImpl implements TaxPaymentService {

    /* private static final String reqUrl = "https://rkdemo.aktors.ee/proto/pay?" +
            "amount=20.0&currency=USD&payerData=%d&referenceNumber=1213129552&paymentTime=%d"; */

    @Value("${proxy.url.pay.tax}")
    private String reqUrl;

    private static final String OK_RESPONSE = "OK";

    private final OkHttpClient client;

    @Autowired
    public TaxPaymentServiceImpl(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public boolean pay(Deal deal) {
        log.info("pay tax: {}", deal);
        String url = String.format(reqUrl, deal.getBuyerIdCode(), System.currentTimeMillis());
        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
        }
        catch (IOException e) {
            log.error("Payment failed", e);
            return false;
        }

        String responseString;
        try {
            ResponseBody body = response.body();
            if (body == null) {
                log.warn("no response from payment service");
                return false;
            }

            responseString = body.string().trim();
            log.info("response from payment service: {}", responseString);
        }
        catch (IOException e) {
            return false;
        }

        if (OK_RESPONSE.equals(responseString)) {
            deal.setPaid(true);
            return true;
        }

        return false;
    }
}
