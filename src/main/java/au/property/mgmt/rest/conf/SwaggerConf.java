package au.property.mgmt.rest.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;

/**
 * @author taaviv @ 26.10.18
 */
@Configuration
@EnableSwagger2
public class SwaggerConf {

    @Value("${swagger.base.path:}")
    private String basePath;

    @Value("${swagger.host:}")
    private String host;

    @Bean
    public Docket api(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2).
                pathProvider(new RelativePathProvider(servletContext) {

                    @Override
                    public String getApplicationBasePath() {
                        return basePath + super.getApplicationBasePath();
                    }

                }).host(host)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
