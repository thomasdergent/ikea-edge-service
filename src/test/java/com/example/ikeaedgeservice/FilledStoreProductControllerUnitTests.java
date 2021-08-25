package com.example.ikeaedgeservice;

import com.example.ikeaedgeservice.model.Product;
import com.example.ikeaedgeservice.model.Store;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class FilledStoreProductControllerUnitTests {

    @Value("${storeservice.baseurl}")
    private String storeServiceBaseUrl;

    @Value("${productservice.baseurl}")
    private String productServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Store store1 = new Store("IKEA Hasselt", "Limburg", "Hasselt", "teststraat", 1);
    private Store store2 = new Store("IKEA Wilrijk", "Antwerpen", "Wilrijk", "teststraat", 2);

    private Product product1 = new Product("IKEA Hasselt", "Linmon chair", "Bureaustoel","omschrijving", "afbeelding", "abc123", true, "Leer", "Spray", "Recycleerbaar", 500, 200.00, "130cm");
    private Product product2 = new Product("IKEA Hasselt", "Linmon desk", "Bureau","omschrijving", "afbeelding", "abc456", true, "Hout", "Vochtbestendig", "Recycleerbaar", 600, 75.00, "140cmx80cm");
    private Product product3 = new Product("IKEA Wilrijk", "Linmon desk", "Bureau","omschrijving", "afbeelding", "123abc", true, "Hout", "Vochtbestendig", "Recycleerbaar", 600, 75.00, "140cmx80cm");

    private List<Product> allProductsForStoreCategoryBureau = Arrays.asList(product3);
    private List<Product> allProductsForStore1 = Arrays.asList(product1, product2);;
    private List<Store> allStores = Arrays.asList(store1, store2);

    @BeforeEach
    public void initializeMockserver() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetProductByStoreNameAndArticleNumber_thenReturnFilledStoreProductJson() throws Exception {

        //Get Store 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/store/IKEA%20Hasselt")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(store1))
                );

        // GET all Products for Store 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/store/IKEA%20Hasselt/article/" + product1.getArticleNumber())))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(product1))
                );

        mockMvc.perform(get("/store/{storeName}/article/{articleNumber}", store1.getStoreName(), product1.getArticleNumber()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$.province", is("Limburg")))
                .andExpect(jsonPath("$.city", is("Hasselt")))
                .andExpect(jsonPath("$.street", is("teststraat")))
                .andExpect(jsonPath("$.number", is(1)))
                .andExpect(jsonPath("$.categoryProducts[0].name", is("Linmon chair")))
                .andExpect(jsonPath("$.categoryProducts[0].category", is("Bureaustoel")))
                .andExpect(jsonPath("$.categoryProducts[0].description", is("omschrijving")))
                .andExpect(jsonPath("$.categoryProducts[0].image", is("afbeelding")))
                .andExpect(jsonPath("$.categoryProducts[0].articleNumber", is("abc123")))
                .andExpect(jsonPath("$.categoryProducts[0].delivery", is(true)))
                .andExpect(jsonPath("$.categoryProducts[0].material", is("Leer")))
                .andExpect(jsonPath("$.categoryProducts[0].maintenance", is("Spray")))
                .andExpect(jsonPath("$.categoryProducts[0].environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.categoryProducts[0].stock", is(500)))
                .andExpect(jsonPath("$.categoryProducts[0].price", is(200.00)))
                .andExpect(jsonPath("$.categoryProducts[0].size", is("130cm")));
    }

    @Test
    public void whenGetProductsByStoreName_thenReturnFilledStoreProductsJson() throws Exception {

        //Get Store 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/store/IKEA%20Hasselt")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(store1))
                );

        // GET all Products for Store 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/products/IKEA%20Hasselt")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allProductsForStore1))
                );

        mockMvc.perform(get("/products/store/{storeName}", store1.getStoreName()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$.province", is("Limburg")))
                .andExpect(jsonPath("$.city", is("Hasselt")))
                .andExpect(jsonPath("$.street", is("teststraat")))
                .andExpect(jsonPath("$.number", is(1)))
                .andExpect(jsonPath("$.categoryProducts[0].name", is("Linmon chair")))
                .andExpect(jsonPath("$.categoryProducts[0].category", is("Bureaustoel")))
                .andExpect(jsonPath("$.categoryProducts[0].description", is("omschrijving")))
                .andExpect(jsonPath("$.categoryProducts[0].image", is("afbeelding")))
                .andExpect(jsonPath("$.categoryProducts[0].articleNumber", is("abc123")))
                .andExpect(jsonPath("$.categoryProducts[0].delivery", is(true)))
                .andExpect(jsonPath("$.categoryProducts[0].material", is("Leer")))
                .andExpect(jsonPath("$.categoryProducts[0].maintenance", is("Spray")))
                .andExpect(jsonPath("$.categoryProducts[0].environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.categoryProducts[0].stock", is(500)))
                .andExpect(jsonPath("$.categoryProducts[0].price", is(200.00)))
                .andExpect(jsonPath("$.categoryProducts[0].size", is("130cm")))
                .andExpect(jsonPath("$.categoryProducts[1].name", is("Linmon desk")))
                .andExpect(jsonPath("$.categoryProducts[1].category", is("Bureau")))
                .andExpect(jsonPath("$.categoryProducts[1].description", is("omschrijving")))
                .andExpect(jsonPath("$.categoryProducts[1].image", is("afbeelding")))
                .andExpect(jsonPath("$.categoryProducts[1].articleNumber", is("abc456")))
                .andExpect(jsonPath("$.categoryProducts[1].delivery", is(true)))
                .andExpect(jsonPath("$.categoryProducts[1].material", is("Hout")))
                .andExpect(jsonPath("$.categoryProducts[1].maintenance", is("Vochtbestendig")))
                .andExpect(jsonPath("$.categoryProducts[1].environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.categoryProducts[1].stock", is(600)))
                .andExpect(jsonPath("$.categoryProducts[1].price", is(75.00)))
                .andExpect(jsonPath("$.categoryProducts[1].size", is("140cmx80cm")));
    }

    @Test
    public void whenGetProductsByStoreNameAndCategory_thenReturnFilledStoreProductsJson() throws Exception {

        //Get Store 2 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/store/IKEA%20Wilrijk")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(store2))
                );

        // GET all Products for Store 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/store/IKEA%20Wilrijk/category/Bureau")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allProductsForStoreCategoryBureau))
                );

        mockMvc.perform(get("/store/{storeName}/category/{category}", store2.getStoreName(), "Bureau"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName", is("IKEA Wilrijk")))
                .andExpect(jsonPath("$.province", is("Antwerpen")))
                .andExpect(jsonPath("$.city", is("Wilrijk")))
                .andExpect(jsonPath("$.street", is("teststraat")))
                .andExpect(jsonPath("$.number", is(2)))
                .andExpect(jsonPath("$.categoryProducts[0].name", is("Linmon desk")))
                .andExpect(jsonPath("$.categoryProducts[0].category", is("Bureau")))
                .andExpect(jsonPath("$.categoryProducts[0].description", is("omschrijving")))
                .andExpect(jsonPath("$.categoryProducts[0].image", is("afbeelding")))
                .andExpect(jsonPath("$.categoryProducts[0].articleNumber", is("123abc")))
                .andExpect(jsonPath("$.categoryProducts[0].delivery", is(true)))
                .andExpect(jsonPath("$.categoryProducts[0].material", is("Hout")))
                .andExpect(jsonPath("$.categoryProducts[0].maintenance", is("Vochtbestendig")))
                .andExpect(jsonPath("$.categoryProducts[0].environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.categoryProducts[0].stock", is(600)))
                .andExpect(jsonPath("$.categoryProducts[0].price", is(75.00)))
                .andExpect(jsonPath("$.categoryProducts[0].size", is("140cmx80cm")));
    }


    @Test
    public void whenAddProductToStore_thenReturnProductJson() throws Exception {

        Product product4Store2 = new Product("IKEA Wilrijk", "Linmon chair", "Bureaustoel","omschrijving", "afbeelding", "456abc", true, "Leer", "Spray", "Recycleerbaar", 500, 200.00, "130cm");

        //Post product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/product")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(product4Store2))
                );

        mockMvc.perform(post("/product")
                .content(mapper.writeValueAsString(product4Store2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName", is("IKEA Wilrijk")))
                .andExpect(jsonPath("$.name", is("Linmon chair")))
                .andExpect(jsonPath("$.category", is("Bureaustoel")))
                .andExpect(jsonPath("$.description", is("omschrijving")))
                .andExpect(jsonPath("$.image", is("afbeelding")))
                .andExpect(jsonPath("$.articleNumber", is("456abc")))
                .andExpect(jsonPath("$.delivery", is(true)))
                .andExpect(jsonPath("$.material", is("Leer")))
                .andExpect(jsonPath("$.maintenance", is("Spray")))
                .andExpect(jsonPath("$.environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.stock", is(500)))
                .andExpect(jsonPath("$.price", is(200.00)))
                .andExpect(jsonPath("$.size", is("130cm")));
    }

    @Test
    public void whenUpdateProductFromStore_thenReturnProductJson() throws Exception {

        Product updatedProduct1Store1 = new Product("IKEA Hasselt", "Linmon chair", "Bureaustoel", "omschrijving", "afbeelding","abc123", true, "Leer", "Spray", "Recycleerbaar", 400, 200.00, "130cm");

        //Update product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/store/IKEA%20Hasselt/article/" + product1.getArticleNumber())))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedProduct1Store1))
                );


        mockMvc.perform(put("/store/" + updatedProduct1Store1.getStoreName() + "/article/" + updatedProduct1Store1.getArticleNumber())
                .content(mapper.writeValueAsString(updatedProduct1Store1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$.name", is("Linmon chair")))
                .andExpect(jsonPath("$.category", is("Bureaustoel")))
                .andExpect(jsonPath("$.description", is("omschrijving")))
                .andExpect(jsonPath("$.image", is("afbeelding")))
                .andExpect(jsonPath("$.articleNumber", is("abc123")))
                .andExpect(jsonPath("$.delivery", is(true)))
                .andExpect(jsonPath("$.material", is("Leer")))
                .andExpect(jsonPath("$.maintenance", is("Spray")))
                .andExpect(jsonPath("$.environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.stock", is(400)))
                .andExpect(jsonPath("$.price", is(200.00)))
                .andExpect(jsonPath("$.size", is("130cm")));
    }

    @Test
    public void whenDeleteProductFromStore_thenStatusOK() throws Exception {

        //Delete product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/product/store/IKEA%20Hasselt/article/" + product1.getArticleNumber())))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/store/IKEA Hasselt/article/" + product1.getArticleNumber())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteProductFromStore_thenStatusNotFound() throws Exception {

        //Delete product for Store 1 from badrequest
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/product/store/IKEA%20Hasselt/article/badrequest")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                );

        mockMvc.perform(delete("/store/IKEA Hasselt/article/badrequest")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenGetStores_thenReturnStoresJson() throws Exception {

        //Get all stores
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/stores")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStores))
                );

        mockMvc.perform(get("/stores"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$[0].province", is("Limburg")))
                .andExpect(jsonPath("$[0].city", is("Hasselt")))
                .andExpect(jsonPath("$[0].street", is("teststraat")))
                .andExpect(jsonPath("$[0].number", is(1)))
                .andExpect(jsonPath("$[1].storeName", is("IKEA Wilrijk")))
                .andExpect(jsonPath("$[1].province", is("Antwerpen")))
                .andExpect(jsonPath("$[1].city", is("Wilrijk")))
                .andExpect(jsonPath("$[1].street", is("teststraat")))
                .andExpect(jsonPath("$[1].number", is(2)));
    }
}
