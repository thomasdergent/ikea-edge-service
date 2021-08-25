//package com.example.ikeaedgeservice;
//import com.example.ikeaedgeservice.model.Product;
//import com.example.ikeaedgeservice.model.Store;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.client.ExpectedCount;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.client.RestTemplate;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class FilledStoreProductControllerIntegerationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Value("${storeservice.baseurl}")
//    private String storeServiceBaseUrl;
//
//    @Value("${productservice.baseurl}")
//    private String productServiceBaseUrl;
//
//    private ObjectMapper mapper = new ObjectMapper();
//
//
//    private Store store1 = new Store("IKEA dfdf", "Limburg", "Hasselt", "teststraat", 1);
//    private Store store2 = new Store("IKEA Wilrijk", "Antwerpen", "Wilrijk", "teststraat", 2);
//
//    private Product product1 = new Product("IKEA Hasselt", "Linmon chair", "Bureaustoel","omschrijving", "afbeelding", "abc123", true, "Leer", "Spray", "Recycleerbaar", 500, 200.00, "130cm");
//    private Product product2 = new Product("IKEA Hasselt", "Linmon desk", "Bureau","omschrijving", "afbeelding", "abc456", true, "Hout", "Vochtbestendig", "Recycleerbaar", 600, 75.00, "140cmx80cm");
//
//    public void givenReview_whenGetReviewByUserIdAndISBN_thenReturnJsonReview() throws Exception {
//
//        mockMvc.perform(post("http://" + storeServiceBaseUrl + "/store")
//                .content(mapper.writeValueAsString(store2))
//                .contentType(MediaType.APPLICATION_JSON));
//
//        restTemplate.postForObject("http://" + productServiceBaseUrl + "/product",
//                product1, Product.class);
//
//        restTemplate.postForObject("http://" + storeServiceBaseUrl + "/store",
//                store1, Store.class);
//    }
//
//
//    @PostMapping("/product")
//    public Product addProduct(@RequestBody Product product) {
//
//         Product product1 = new Product("IKEA Hasselt", "Linmon chair", "Bureaustoel", "omschrijving", "afbeelding", "abc123", true, "Leer", "Spray", "Recycleerbaar", 500, 200.00, "130cm");
//
//        return restTemplate.postForObject("http://" + productServiceBaseUrl + "/product",
//                product1, Product.class);
//    }
//}
