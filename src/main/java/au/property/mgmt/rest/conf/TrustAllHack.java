package au.property.mgmt.rest.conf;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author taaviv @ 27.10.18
 */
public class TrustAllHack {

    private static final SSLContext trustAllSslContext;
    private static final SSLSocketFactory trustAllSslSocketFactory;

    private static final TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }
            }
    };


    static {
        try {
            trustAllSslContext = SSLContext.getInstance("SSL");
            trustAllSslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            trustAllSslSocketFactory = trustAllSslContext.getSocketFactory();
        }
        catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    public static SSLSocketFactory trustAll() {
        return trustAllSslSocketFactory;
    }

    public static TrustManager trustManager() {
        return trustAllCerts[0];
    }

}
