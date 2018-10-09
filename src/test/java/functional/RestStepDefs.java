package functional;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.prometheus.client.CollectorRegistry;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import be.vdab.demo.stockmanager.StockManagerApplication;
import be.vdab.demo.stockmanager.model.Stock;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = StockManagerApplication.class)
@TestPropertySource(properties = {"management.port=0"})
public class RestStepDefs {

    @Autowired
    private TestRestTemplate restTemplate;

    private List<Stock> stocks;

    @Given("^the application has been initialised with test data$")
    public void init() {
        //the default profile loads synthetic stocks
    }

    @When("^the client gets all stocks$")
    public void getAllStocks() {
        ResponseEntity<List<Stock>> response = restTemplate.exchange(
                "/stocks/v1.0",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Stock>>(){});
        stocks = response.getBody();
    }

    @Then("^a list of (.*) stocks will be returned$")
    public void assertListOfStocksLength(int length) {
        assertThat(stocks, hasSize(length));
    }

    @Then("^the stock at index (.*) will have the sku (.*)$")
    public void assertStockHasSku(int stockIndex, String sku) {
        assertThat(stocks.get(stockIndex).getSku(), is(sku));
    }

}
