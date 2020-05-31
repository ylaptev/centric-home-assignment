package com.yl.demo;

import com.yl.demo.web.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void productsNoCategoryShouldReturnEmptyList() {
        ResponseEntity<List<ProductDto>> result = sendGetRequestFindProducts(null, null, null);
        List<ProductDto> products = result.getBody();
        assertThat(products.isEmpty()).isEqualTo(true);
    }


    @Test
    public void testCreateProductRed() {
        ResponseEntity<ProductDto> result = sendPostRequestCreateProduct(0);

        ProductDto dto = result.getBody();
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isNotNull();
        assertThat(dto.getCreated_at()).isNotNull();
        assertThat(dto.getName()).isEqualTo("Red Shirt");
        assertThat(dto.getCategory()).isEqualTo("apparel");
    }

    @Test
    public void testCreateAndGetSingleProduct() {
        sendPostRequestCreateProduct(1);

        ResponseEntity<List<ProductDto>> result = sendGetRequestFindProducts("apparel", null, "1");

        List<ProductDto> products = result.getBody();

        assertThat(products.size()).isEqualTo((Integer)1);
        ProductDto product = products.get(0);
        assertThat(product.getId()).isNotNull();
        assertThat(product.getCreated_at()).isNotNull();
        assertThat(product.getName()).isEqualTo("Blue Shirt");
        assertThat(product.getCategory()).isEqualTo("apparel");
    }

    @Test
    public void testGetApparelProductListPageSize5() {
        // Create 10 test products
        for (int i = 0; i < 10; i++) {
            pause();
            sendPostRequestCreateProduct(i);
        }

        // Retrieve products, pageSize=5
        ResponseEntity<List<ProductDto>> result = sendGetRequestFindProducts("apparel", null, "5");

        List<ProductDto> products = result.getBody();

        // pageSize = 5
        assertThat(products.size()).isEqualTo((Integer) 5);

        ProductDto product = products.get(0);
        assertThat(product.getId()).isNotNull();
        assertThat(product.getCreated_at()).isNotNull();
        assertThat(product.getName()).isEqualTo("Orange Shirt");
        assertThat(product.getCategory()).isEqualTo("apparel");
    }

    @Test
    public void testGetApparelProductListPageSize3Page1() throws Exception {
        // Create 12 test products
        for (int i = 3; i < 15; i++) {
            pause();
            sendPostRequestCreateProduct(i);
        }

        // Retrieve products, pageSize=5
        ResponseEntity<List<ProductDto>> result = sendGetRequestFindProducts("apparel", "1", "3");

        List<ProductDto> products = result.getBody();

        // pageSize = 3
        assertThat(products.size()).isEqualTo((Integer) 3);

        ProductDto product = products.get(0);
        assertThat(product.getId()).isNotNull();
        assertThat(product.getCreated_at()).isNotNull();
        assertThat(product.getName()).isEqualTo("Green Shirt");
        assertThat(product.getCategory()).isEqualTo("apparel");
    }

    @Test
    public void testGetUnknownProductListShouldbeEmpty() {
        sendPostRequestCreateProduct(0);

        // Retrieve products, pageSize=5
        ResponseEntity<List<ProductDto>> result = sendGetRequestFindProducts("unknown", null, null);

        List<ProductDto> products = result.getBody();

        // Expect no results
        assertThat(products.isEmpty()).isEqualTo(true);

    }


    // test helper to use in multiple tests: create product using POST
    private ResponseEntity<ProductDto> sendPostRequestCreateProduct(int n) {
        ProductDto product = TestData.buildTestProductDto(n);
        return this.restTemplate.postForEntity("http://localhost:" + port + "/v1/products", product, ProductDto.class);
    }

    // test helper to send Get request to retrieve products by category
    private ResponseEntity<List<ProductDto>> sendGetRequestFindProducts(String category, String page, String pageSize) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/v1/products")
                .queryParam("category", category)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize);

        return this.restTemplate.exchange(builder.toUriString(),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ProductDto>>() {});
    }

    private static void pause() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}