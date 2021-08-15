package com.example.ikeaedgeservice.controller;

import com.example.ikeaedgeservice.model.CategoryProduct;
import com.example.ikeaedgeservice.model.FilledStoreProduct;
import com.example.ikeaedgeservice.model.Product;
import com.example.ikeaedgeservice.model.Store;
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

@RestController
public class FilledStoreProductController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${storeservice.baseurl}")
    private String storeServiceBaseUrl;

    @Value("${productservice.baseurl}")
    private String productServiceBaseUrl;

    //Get specific product from specific store
    @GetMapping("/store/{storeName}/article/{articleNumber}")
    public FilledStoreProduct getRankingByUserIdAndISBN(@PathVariable String storeName, @PathVariable String articleNumber) {

        Store store =
                restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/" + storeName,
                        Store.class);

        Product product =
                restTemplate.getForObject("http://" + productServiceBaseUrl + "/store/" + storeName + "/article/" + articleNumber,
                        Product.class);

        return new FilledStoreProduct(store, product);
    }

//    //Get all products from category
//    @GetMapping("/category/{category}")
//    public List<FilledStoreProduct> getMeubelsByCategory(@PathVariable String category) {
//
//        List<FilledStoreProduct> returnList = new ArrayList();
//
//        ResponseEntity<List<Product>> responseEntityReviews =
//                restTemplate.exchange("http://" + productServiceBaseUrl + "/products/category/{category}",
//                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
//                        }, category);
//
//        List<Product> products = responseEntityReviews.getBody();
//
//        for (Product product :
//                products) {
//            Store store =
//                    restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/{storeName}",
//                            Store.class, product.getStoreName());
//
//            returnList.add(new FilledStoreProduct(store, product));
//        }
//
//        return returnList;
//    }

    //Get all products from specific store
    @GetMapping("/products/store/{storeName}")
    public FilledStoreProduct getProductsByStoreName(@PathVariable String storeName) {
        Store store = restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/{storeName}", Store.class, storeName);

        ResponseEntity<List<Product>> responseEntityProducts = restTemplate.exchange("http://" + productServiceBaseUrl + "/products/{storeName}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        }, storeName);

        return new FilledStoreProduct(store, responseEntityProducts.getBody());
    }

    //Get all products from a specific category for a specific store
    @GetMapping("/store/{storeName}/category/{category}")
    public FilledStoreProduct testje(@PathVariable String storeName, @PathVariable String category) {
        Store store = restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/{storeName}", Store.class, storeName);

        ResponseEntity<List<Product>> responseEntityProducts = restTemplate.exchange("http://" + productServiceBaseUrl + "/store/" + storeName + "/category/{category}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        }, category);

        return new FilledStoreProduct(store, responseEntityProducts.getBody());
    }

//    @PostMapping("/product")
//    public FilledStoreProduct addProduct(@RequestParam String storeName, @RequestParam String name, @RequestParam String category, @RequestParam String articleNumber, @RequestParam boolean delivery, @RequestParam String material, @RequestParam String maintenance, @RequestParam String environment, @RequestParam int stock, @RequestParam double price, @RequestParam String size) {
//
//        Product product = restTemplate.postForObject("http://" + productServiceBaseUrl + "/product", new Product(storeName, name, category, articleNumber, delivery, material, maintenance, environment, stock, price, size), Product.class);
//
//        Store store =
//                restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/{storeName}",
//                        Store.class, storeName);
//
//
//        return new FilledStoreProduct(store, product);
//    }

    //Post product
    @PostMapping("/product")
    public Product addProduct(@RequestBody Product product) {

        return restTemplate.postForObject("http://" + productServiceBaseUrl + "/product",
                product, Product.class);
    }

    //Update specific product from specific store
    @PutMapping("/store/{storeName}/article/{articleNumber}")
    public Product updateProduct(@PathVariable String storeName, @PathVariable String articleNumber, @RequestBody Product updatedProduct) {

        ResponseEntity<Product> responseEntityReview =
                restTemplate.exchange("http://" + productServiceBaseUrl + "/store/" + storeName + "/article/" + articleNumber,
                        HttpMethod.PUT, new HttpEntity<>(updatedProduct), Product.class);

        return responseEntityReview.getBody();
    }

//    //werkt basic
//    @PutMapping("/products")
//    public Product update(@RequestBody Product updatedProduct) {
//
//        ResponseEntity<Product> responseEntityReview =
//                restTemplate.exchange("http://" + productServiceBaseUrl + "/product",
//                        HttpMethod.PUT, new HttpEntity<>(updatedProduct), Product.class);
//
//        return responseEntityReview.getBody();
//    }


//    //werkt maar is tering lang
//    @PutMapping("/product")
//    public FilledStoreProduct updateProduct(@RequestParam String storeName, @RequestParam String name, @RequestParam String category, @RequestParam String articleNumber, @RequestParam boolean delivery, @RequestParam String material, @RequestParam String maintenance, @RequestParam String environment, @RequestParam int stock, @RequestParam double price, @RequestParam String size) {
//
//        Product product =
//                restTemplate.getForObject("http://" + productServiceBaseUrl + "/product/store/" + storeName + "/article/" + articleNumber,
//                        Product.class);
//        product.setName(name);
//        product.setCategory((category));
//        product.setArticleNumber(articleNumber);
//        product.setDelivery(delivery);
//        product.setMaterial(material);
//        product.setMaintenance(maintenance);
//        product.setEnvironment(environment);
//        product.setStock(stock);
//        product.setPrice(price);
//        product.setSize(size);
//
//        ResponseEntity<Product> responseEntityReview =
//                restTemplate.exchange("http://" + productServiceBaseUrl + "/product",
//                        HttpMethod.PUT, new HttpEntity<>(product), Product.class);
//
//        Product retrievedProduct = responseEntityReview.getBody();
//
//        Store store =
//                restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/{storeName}",
//                        Store.class, storeName);
//
//        return new FilledStoreProduct(store, retrievedProduct);
//    }

    //Delete specific product from specific store
    @DeleteMapping("/store/{storeName}/article/{articleNumber}")
    public ResponseEntity deleteProduct(@PathVariable String storeName, @PathVariable String articleNumber) {

        try {

            restTemplate.delete("http://" + productServiceBaseUrl + "/product/store/" + storeName + "/article/" + articleNumber);

            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

    //Get all stores
    @GetMapping("/stores")
    public List<Store> getStores() {

        ResponseEntity<List<Store>> responseEntityProducts = restTemplate.exchange("http://" + storeServiceBaseUrl + "/stores", HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>() {
        });

        return responseEntityProducts.getBody();
    }
}
