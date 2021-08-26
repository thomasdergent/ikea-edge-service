package com.example.ikeaedgeservice.controller;

import com.example.ikeaedgeservice.model.FilledProductStore;
import com.example.ikeaedgeservice.model.Product;
import com.example.ikeaedgeservice.model.Store;
import com.example.ikeaedgeservice.model.StoreStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class FilledProductStoreController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${productservice.baseurl}")
    private String productServiceBaseUrl;

    @Value("${storeservice.baseurl}")
    private String storeServiceBaseUrl;


    //Get specific product from specific store
    @GetMapping("/product/{articleNumber}")
    public FilledProductStore getProductByArticleNumber(@PathVariable String articleNumber) {

        Product product =
                restTemplate.getForObject("http://" + productServiceBaseUrl + "/product/" + articleNumber,
                        Product.class);

        ResponseEntity<List<Store>> responseEntityStores = restTemplate.exchange("http://" + storeServiceBaseUrl + "/stores/product/{articleNumber}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>() {
        }, articleNumber);

        return new FilledProductStore(product, responseEntityStores.getBody());
    }

    //Get specific product from specific store
    @GetMapping("/products")
    public List<FilledProductStore> getProducts() {

        List<FilledProductStore> returnList = new ArrayList();

        ResponseEntity<List<Product>> responseEntityProducts = restTemplate.exchange("http://" + productServiceBaseUrl + "/products", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        });

        List<Product> products = responseEntityProducts.getBody();

        for (Product product : products) {
            ResponseEntity<List<Store>> responseEntityStores = restTemplate.exchange("http://" + storeServiceBaseUrl + "/stores/product/{articleNumber}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>() {
            }, product.getArticleNumber());

            returnList.add(new FilledProductStore(product, responseEntityStores.getBody()));
        }
        return returnList;
    }

    //Get specific product from specific store
    @GetMapping("/products/{category}")
    public List<FilledProductStore> getProductsByCategory(@PathVariable String category) {

        List<FilledProductStore> returnList = new ArrayList();

        ResponseEntity<List<Product>> responseEntityProducts = restTemplate.exchange("http://" + productServiceBaseUrl + "/products/category/{category}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        }, category);

        List<Product> products = responseEntityProducts.getBody();

        for (Product product : products) {
            ResponseEntity<List<Store>> responseEntityStores = restTemplate.exchange("http://" + storeServiceBaseUrl + "/stores/product/{articleNumber}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>() {
            }, product.getArticleNumber());

            returnList.add(new FilledProductStore(product, responseEntityStores.getBody()));
        }
        return returnList;
    }

    //Get store from specific product
    @GetMapping("/product/{articleNumber}/store/{storeName}")
    public Store getStoreByArticleNumberAndStoreName(@PathVariable String articleNumber, @PathVariable String storeName) {


        Store store = restTemplate.getForObject("http://" + storeServiceBaseUrl + "/product/{articleNumber}/store/{storeName}", Store.class, articleNumber, storeName);

        return store;
    }

    //Post product
    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product) {

        return restTemplate.postForObject("http://" + productServiceBaseUrl + "/product",
                product, Product.class);
    }

    //Update specific product from specific store
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/product/{articleNumber}")
    public Product updateProduct(@PathVariable String articleNumber, @RequestBody Product updatedProduct) {

        ResponseEntity<Product> responseEntityProduct =
                restTemplate.exchange("http://" + productServiceBaseUrl + "/product/" + articleNumber,
                        HttpMethod.PUT, new HttpEntity<>(updatedProduct), Product.class);

        return responseEntityProduct.getBody();
    }

    //Delete specific product from specific store
    @DeleteMapping("/product/{articleNumber}")
    public ResponseEntity deleteProduct(@PathVariable String articleNumber) {

        try {

            restTemplate.delete("http://" + productServiceBaseUrl + "/product/" + articleNumber);

            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    //Post store
    @PostMapping("/store")
    public Store addStore(@RequestBody Store store) {

        return restTemplate.postForObject("http://" + storeServiceBaseUrl + "/store",
                store, Store.class);
    }

    //Update specific store
    @PutMapping("/product/{articleNumber}/store/{storeName}")
    public Store updateStore(@PathVariable String articleNumber, @PathVariable String storeName, @RequestBody Store updatedStore) {

        ResponseEntity<Store> responseEntityStore =
                restTemplate.exchange("http://" + storeServiceBaseUrl + "/product/" + articleNumber + "/store/" + storeName,
                        HttpMethod.PUT, new HttpEntity<>(updatedStore), Store.class);

        return responseEntityStore.getBody();
    }

    //Delete specific store
    @DeleteMapping("/product/{articleNumber}/store/{storeName}")
    public ResponseEntity deleteStore(@PathVariable String articleNumber, @PathVariable String storeName) {

        try {

            restTemplate.delete("http://" + storeServiceBaseUrl + "/product/" + articleNumber + "/store/" + storeName);

            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }
}
//