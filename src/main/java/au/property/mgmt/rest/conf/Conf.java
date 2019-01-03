package au.property.mgmt.rest.conf;

import okhttp3.OkHttpClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.net.ssl.X509TrustManager;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author taaviv @ 25.10.18
 */
@Configuration
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan(basePackages = {"au.property.mgmt.rest"})
public class Conf {

    @Value("${elasticsearch.host}")
    private String esHost;

    @Value("${elasticsearch.port}")
    private int esPort;

    @Value("${elasticsearch.clustername}")
    private String esClusterName;


    @Bean(destroyMethod = "close")
    public Client client() throws UnknownHostException {
        TransportAddress tpa = new TransportAddress(InetAddress.getByName(esHost), esPort);
        return new PreBuiltTransportClient(
                Settings.builder().put(ClusterName.CLUSTER_NAME_SETTING.getKey(), esClusterName).build()
        ).addTransportAddress(tpa);
    }

    @Bean
    public OkHttpClient trustAllClient() {
        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = client.newBuilder();
        builder.sslSocketFactory(TrustAllHack.trustAll(), (X509TrustManager) TrustAllHack.trustManager());
        builder.hostnameVerifier((hostname, session) -> true);
        return builder.build();
    }

}
