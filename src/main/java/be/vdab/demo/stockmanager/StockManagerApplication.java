package be.vdab.demo.stockmanager;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class StockManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockManagerApplication.class, args);
    }

    // Make a new Docket-bean for every other version of the API !
    // They will then appear in the Swagger UI
    @Bean
    public Docket apiV1_0() {
        return new Docket(DocumentationType.SWAGGER_2)
                //.useDefaultResponseMessages(false)
                .groupName("stockmanager-api-1.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("be.vdab.demo.stockmanager.resources"))
                .paths(regex("/stocks/v1.0.*"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("StockManager API").description("Documentation StockManager API v1.0").build());
    }

    // fix clash between hikari config namespace (app.datasource.*) and spring default namespace (spring.datasource.*)
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    // fix clash between hikari config namespace (app.datasource.*) and spring default namespace (spring.datasource.*)
    @Bean
    @ConfigurationProperties("app.datasource")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class)
                .build();
    }
}
