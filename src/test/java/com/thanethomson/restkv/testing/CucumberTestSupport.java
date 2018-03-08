package com.thanethomson.restkv.testing;

import com.thanethomson.restkv.Application;
import com.thanethomson.restkv.storage.KeyValueStore;
import cucumber.api.CucumberOptions;
import cucumber.api.PendingException;
import cucumber.api.java8.En;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = "pretty",
        features = "src/test/resources/features"
)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTestSupport implements En {

    @Autowired
    private KeyValueStore keyValueStore;

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> latestApiResult;

    public CucumberTestSupport() {
        Given("^some test data in our store$", () -> {
            keyValueStore.deleteAll().get();
            keyValueStore.put("name", "Michael").get();
            keyValueStore.put("surname", "Anderson").get();
            keyValueStore.put("1234", "5678").get();
        });
        When("^we do a GET request for \"([^\"]*)\"$", (String keyId) -> {
            latestApiResult = restTemplate.getForEntity("/"+keyId, String.class);
        });
        Then("^we should receive a successful response of \"([^\"]*)\"$", (String expectedResponse) -> {
            assertThat(latestApiResult.getBody()).isEqualTo(expectedResponse);
        });
        Given("^an empty data store$", () -> {
            keyValueStore.deleteAll().get();
        });
        When("^we do a PUT request to store \"([^\"]*)\" in key \"([^\"]*)\"$", (String value, String key) -> {
            latestApiResult = restTemplate.exchange(RequestEntity.put(new URI("/"+key)).body(value), String.class);
        });
        Then("^we should receive an HTTP status code of \"([^\"]*)\"$", (String statusCode) -> {
            assertThat(latestApiResult.getStatusCode().value()).isEqualTo(Integer.valueOf(statusCode));
        });
        And("^we should find the value of \"([^\"]*)\" to be \"([^\"]*)\"$", (String key, String value) -> {
            assertThat(keyValueStore.get(key).get()).isEqualTo(value);
        });
        When("^we do a DELETE request for \"([^\"]*)\"$", (String key) -> {
            latestApiResult = restTemplate.exchange(RequestEntity.delete(new URI("/"+key)).build(), String.class);
        });
        And("^we should find a null value for \"([^\"]*)\" in our store$", (String key) -> {
            assertThat(keyValueStore.get(key).get()).isNull();
        });
    }

}
