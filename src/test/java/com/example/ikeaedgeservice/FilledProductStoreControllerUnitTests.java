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
public class FilledProductStoreControllerUnitTests {

    @Value("${productservice.baseurl}")
    private String productServiceBaseUrl;

    @Value("${storeservice.baseurl}")
    private String storeServiceBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    private Product product1 = new Product("Linmon chair", "Bureaustoel", "omschrijving", "afbeelding", "unitTest123", true, "Leer", "Spray", "Recycleerbaar", 500.00, "130cm");
    private Product product2 = new Product("Linmon desk", "Bureau", "omschrijving", "afbeelding", "unitTest456", true, "Hout", "Vochtbestendig", "Recycleerbaar", 75.00, "140cmx80cm");

    Store store1 = new Store("IKEA Hasselt", "unitTest123", 100);
    Store store2 = new Store("IKEA Wilrijk", "unitTest123", 200);
    Store store3 = new Store("IKEA Hasselt", "unitTest456", 300);

    //private List<Product> allProductsForStoreCategoryBureau = Arrays.asList(store1);
    private List<Product> allProducts = Arrays.asList(product1, product2);
    private List<Product> allProductsByCategoryBureaustoel = Arrays.asList(product1);
    private List<Store> allStoresProduct1 = Arrays.asList(store1, store2);
    private List<Store> allStoresProduct2 = Arrays.asList(store3);


    @BeforeEach
    public void initializeMockserver() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetProductByArticleNumber_thenReturnFilledStoreProductJson() throws Exception {

        //Get Product 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/product/unitTest123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(product1))
                );

        // GET all Store for Store 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/stores/product/unitTest123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStoresProduct1))
                );

        mockMvc.perform(get("/product/{articleNumber}", "unitTest123"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Linmon chair")))
                .andExpect(jsonPath("$.category", is("Bureaustoel")))
                .andExpect(jsonPath("$.description", is("omschrijving")))
                .andExpect(jsonPath("$.image", is("afbeelding")))
                .andExpect(jsonPath("$.articleNumber", is("unitTest123")))
                .andExpect(jsonPath("$.delivery", is(true)))
                .andExpect(jsonPath("$.material", is("Leer")))
                .andExpect(jsonPath("$.maintenance", is("Spray")))
                .andExpect(jsonPath("$.environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.price", is(500.00)))
                .andExpect(jsonPath("$.size", is("130cm")))
                .andExpect(jsonPath("$.storeStocks[0].storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$.storeStocks[0].stock", is(100)))
                .andExpect(jsonPath("$.storeStocks[1].storeName", is("IKEA Wilrijk")))
                .andExpect(jsonPath("$.storeStocks[1].stock", is(200)));
    }

    @Test
    public void whenGetProductsByStoreName_thenReturnFilledStoreProductsJson() throws Exception {

        //Get Product 1 info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/products")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allProducts))
                );

        // GET all Store for Store 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/stores/product/unitTest123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStoresProduct1))
                );

        // GET all Store for Store 2
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/stores/product/unitTest456")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStoresProduct2))
                );

        mockMvc.perform(get("/products"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Linmon chair")))
                .andExpect(jsonPath("$[0].category", is("Bureaustoel")))
                .andExpect(jsonPath("$[0].description", is("omschrijving")))
                .andExpect(jsonPath("$[0].image", is("afbeelding")))
                .andExpect(jsonPath("$[0].articleNumber", is("unitTest123")))
                .andExpect(jsonPath("$[0].delivery", is(true)))
                .andExpect(jsonPath("$[0].material", is("Leer")))
                .andExpect(jsonPath("$[0].maintenance", is("Spray")))
                .andExpect(jsonPath("$[0].environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$[0].price", is(500.00)))
                .andExpect(jsonPath("$[0].size", is("130cm")))
                .andExpect(jsonPath("$[0].storeStocks[0].storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$[0].storeStocks[0].stock", is(100)))
                .andExpect(jsonPath("$[0].storeStocks[1].storeName", is("IKEA Wilrijk")))
                .andExpect(jsonPath("$[0].storeStocks[1].stock", is(200)))
                .andExpect(jsonPath("$[1].name", is("Linmon desk")))
                .andExpect(jsonPath("$[1].category", is("Bureau")))
                .andExpect(jsonPath("$[1].description", is("omschrijving")))
                .andExpect(jsonPath("$[1].image", is("afbeelding")))
                .andExpect(jsonPath("$[1].articleNumber", is("unitTest456")))
                .andExpect(jsonPath("$[1].delivery", is(true)))
                .andExpect(jsonPath("$[1].material", is("Hout")))
                .andExpect(jsonPath("$[1].maintenance", is("Vochtbestendig")))
                .andExpect(jsonPath("$[1].environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$[1].price", is(75.00)))
                .andExpect(jsonPath("$[1].size", is("140cmx80cm")))
                .andExpect(jsonPath("$[1].storeStocks[0].storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$[1].storeStocks[0].stock", is(300)));
    }

    @Test
    public void whenGetProductsByStoreNameAndCategory_thenReturnFilledStoreProductsJson() throws Exception {

        //Get Products by category bureaustoel info
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/products/category/Bureaustoel")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allProductsByCategoryBureaustoel))
                );

        // GET all Store for Store 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/stores/product/unitTest123")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(allStoresProduct1))
                );

        mockMvc.perform(get("/products/{category}", "Bureaustoel"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Linmon chair")))
                .andExpect(jsonPath("$[0].category", is("Bureaustoel")))
                .andExpect(jsonPath("$[0].description", is("omschrijving")))
                .andExpect(jsonPath("$[0].image", is("afbeelding")))
                .andExpect(jsonPath("$[0].articleNumber", is("unitTest123")))
                .andExpect(jsonPath("$[0].delivery", is(true)))
                .andExpect(jsonPath("$[0].material", is("Leer")))
                .andExpect(jsonPath("$[0].maintenance", is("Spray")))
                .andExpect(jsonPath("$[0].environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$[0].price", is(500.00)))
                .andExpect(jsonPath("$[0].size", is("130cm")))
                .andExpect(jsonPath("$[0].storeStocks[0].storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$[0].storeStocks[0].stock", is(100)))
                .andExpect(jsonPath("$[0].storeStocks[1].storeName", is("IKEA Wilrijk")))
                .andExpect(jsonPath("$[0].storeStocks[1].stock", is(200)));
    }

//
    @Test
    public void whenAddProductToStore_thenReturnProductJson() throws Exception {

        Product productToAdd = new Product("Alexa chair", "Bureaustoel", "omschrijving", "afbeelding", "123unitTest", true, "Leer", "Spray", "Recycleerbaar", 500.00, "130cm");

        //Post product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/product")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(productToAdd))
                );

        mockMvc.perform(post("/product")
                .content(mapper.writeValueAsString(productToAdd))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Alexa chair")))
                .andExpect(jsonPath("$.category", is("Bureaustoel")))
                .andExpect(jsonPath("$.description", is("omschrijving")))
                .andExpect(jsonPath("$.image", is("afbeelding")))
                .andExpect(jsonPath("$.articleNumber", is("123unitTest")))
                .andExpect(jsonPath("$.delivery", is(true)))
                .andExpect(jsonPath("$.material", is("Leer")))
                .andExpect(jsonPath("$.maintenance", is("Spray")))
                .andExpect(jsonPath("$.environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.price", is(500.00)))
                .andExpect(jsonPath("$.size", is("130cm")));
    }

    @Test
    public void whenUpdateProductFromStore_thenReturnProductJson() throws Exception {

        Product updatedProduct1 = new Product("Linmon chair", "Bureaustoel", "omschrijving", "afbeelding", "unitTest123", true, "Leer", "Spray", "Recycleerbaar", 800.00, "130cm");

        //Update product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/product/" + product1.getArticleNumber())))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedProduct1))
                );


        mockMvc.perform(put("/product/" + product1.getArticleNumber())
                .content(mapper.writeValueAsString(updatedProduct1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Linmon chair")))
                .andExpect(jsonPath("$.category", is("Bureaustoel")))
                .andExpect(jsonPath("$.description", is("omschrijving")))
                .andExpect(jsonPath("$.image", is("afbeelding")))
                .andExpect(jsonPath("$.articleNumber", is("unitTest123")))
                .andExpect(jsonPath("$.delivery", is(true)))
                .andExpect(jsonPath("$.material", is("Leer")))
                .andExpect(jsonPath("$.maintenance", is("Spray")))
                .andExpect(jsonPath("$.environment", is("Recycleerbaar")))
                .andExpect(jsonPath("$.price", is(800.00)))
                .andExpect(jsonPath("$.size", is("130cm")));
    }

    @Test
    public void whenDeleteProductFromStore_thenStatusOK() throws Exception {

        //Delete product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/product/" + product1.getArticleNumber())))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/product/" + product1.getArticleNumber())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteProductFromStore_thenStatusNotFound() throws Exception {

        //Delete product for Store 1 from badrequest
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + productServiceBaseUrl + "/product/badArticleNumber")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                );

        mockMvc.perform(delete("/product/badArticleNumber")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }



    @Test
    public void whenAddStore_thenReturnProductJson() throws Exception {

        Store store1 = new Store("IKEA Wilrijk", "unitTest456", 100);

        //Post product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/store")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(store1))
                );

        mockMvc.perform(post("/store")
                .content(mapper.writeValueAsString(store1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName", is("IKEA Wilrijk")))
                .andExpect(jsonPath("$.articleNumber", is("unitTest456")))
                .andExpect(jsonPath("$.stock", is(100)));
    }

    @Test
    public void whenUpdateStore_thenReturnProductJson() throws Exception {

        Store updatedStore1 = new Store("IKEA Hasselt", "unitTest456", 500);

        //Update product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/product/" + store3.getArticleNumber() + "/store/IKEA%20Hasselt")))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(updatedStore1))
                );


        mockMvc.perform(put("/product/" + updatedStore1.getArticleNumber() + "/store/" + store1.getStoreName())
                .content(mapper.writeValueAsString(updatedStore1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeName", is("IKEA Hasselt")))
                .andExpect(jsonPath("$.articleNumber", is("unitTest456")))
                .andExpect(jsonPath("$.stock", is(500)));
    }

    @Test
    public void whenDeleteStore_thenStatusOK() throws Exception {

        //Delete product for Store 1 from product 1
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/product/" + store1.getArticleNumber() + "/store/IKEA%20Hasselt")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                );

        mockMvc.perform(delete("/product/" + store1.getArticleNumber() + "/store/" + store1.getStoreName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDeleteStore_thenStatusNotFound() throws Exception {

        //Delete product for Store 1 from badrequest
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://" + storeServiceBaseUrl + "/product/" + "badArticleNumber" + "/store/" + "badStoreName")))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                );

        mockMvc.perform(delete("/product/" + "badArticleNumber" + "/store/" + "badStoreName")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }







//
//    @Test
//    public void whenGetStores_thenReturnStoresJson() throws Exception {
//
//        //Get all stores
//        mockServer.expect(ExpectedCount.once(),
//                requestTo(new URI("http://" + storeServiceBaseUrl + "/stores")))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(mapper.writeValueAsString(allStores))
//                );
//
//        mockMvc.perform(get("/stores"))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].storeName", is("IKEA Hasselt")))
//                .andExpect(jsonPath("$[0].province", is("Limburg")))
//                .andExpect(jsonPath("$[0].city", is("Hasselt")))
//                .andExpect(jsonPath("$[0].street", is("teststraat")))
//                .andExpect(jsonPath("$[0].number", is(1)))
//                .andExpect(jsonPath("$[1].storeName", is("IKEA Wilrijk")))
//                .andExpect(jsonPath("$[1].province", is("Antwerpen")))
//                .andExpect(jsonPath("$[1].city", is("Wilrijk")))
//                .andExpect(jsonPath("$[1].street", is("teststraat")))
//                .andExpect(jsonPath("$[1].number", is(2)));
//    }
}
