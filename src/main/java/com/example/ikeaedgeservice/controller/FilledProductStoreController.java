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

@RestController
public class FilledProductStoreController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${productservice.baseurl}")
    private String productServiceBaseUrl;

    @Value("${storeservice.baseurl}")
    private String storeServiceBaseUrl;


    //Get specific product from specific store
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/product/{articleNumber}")
    public FilledProductStore getProductByArticleNumber(@PathVariable String articleNumber) {

        Product product =
                restTemplate.getForObject("http://" + productServiceBaseUrl + "/product/" + articleNumber,
                        Product.class);

        ResponseEntity<List<Store>> responseEntityStores = restTemplate.exchange("http://" + storeServiceBaseUrl + "/stores/product/{articleNumber}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>() {
        }, articleNumber);

        return new FilledProductStore(product, responseEntityStores.getBody());
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

    //Get specific product from specific store
    @CrossOrigin(origins = "http://localhost:4200")
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
    @CrossOrigin(origins = "http://localhost:4200")
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


//    //Get all products from specific store
//    @CrossOrigin(origins = "http://localhost:4200")
//    @GetMapping("/products/store/{storeName}")
//    public FilledProductStore getProductsByStoreName(@PathVariable String storeName) {
//        Store store = restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/{storeName}", Store.class, storeName);
//
//        ResponseEntity<List<Product>> responseEntityProducts = restTemplate.exchange("http://" + productServiceBaseUrl + "/products/{storeName}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
//        }, storeName);
//
//        return new FilledProductStore(store, responseEntityProducts.getBody());
//    }
//
//    //Get all products from a specific category for a specific store
//    @CrossOrigin(origins = "http://localhost:4200")
//    @GetMapping("/store/{storeName}/category/{category}")
//    public FilledProductStore getProductsByStoreNameAndCategory(@PathVariable String storeName, @PathVariable String category) {
//        Store store = restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/{storeName}", Store.class, storeName);
//
//        ResponseEntity<List<Product>> responseEntityProducts = restTemplate.exchange("http://" + productServiceBaseUrl + "/store/" + storeName + "/category/{category}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
//        }, category);
//
//        return new FilledProductStore(store, responseEntityProducts.getBody());
//    }

//    @PostMapping("/product")
//    public FilledStoreProduct addProduct(@RequestParam String storeName, @RequestParam String name, @RequestParam String category, @RequestParam String articleNumber, @RequestParam boolean delivery, @RequestParam String material, @RequestParam String maintenance, @RequestParam String environment, @RequestParam int stock, @RequestParam double price, @RequestParam String size) {
//
//        Product product = restTemplate.postForObject("http://" + productServiceBaseUrl + "/product", new Product(storeName, name, category, articleNumber, delivery, material, maintenance, environment, stock, price, size), Product.class);
//
//        Store store =
//                restTemplate.getForObject("http://" + storeServiceBaseUrl + "/store/{storeName}",
//                        Store.class, storeName)
//
//
//        return new FilledStoreProduct(store, product);
//    }

    //Post product
    @PostMapping("/product")
    @CrossOrigin(origins = "http://localhost:4200")
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
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/product/{articleNumber}")
    public ResponseEntity deleteProduct(@PathVariable String articleNumber) {

        try {

            restTemplate.delete("http://" + productServiceBaseUrl + "/product/" + articleNumber);

            return ResponseEntity.ok().build();
        } catch (Exception e) {

            return ResponseEntity.badRequest().build();
        }
    }

//    //Get all stores
//    @CrossOrigin(origins = "http://localhost:4200")
//    @GetMapping("/stores")
//    public List<Store> getStores() {
//
//        ResponseEntity<List<Store>> responseEntityProducts = restTemplate.exchange("http://" + storeServiceBaseUrl + "/stores", HttpMethod.GET, null, new ParameterizedTypeReference<List<Store>>() {
//        });
//
//        return responseEntityProducts.getBody();
//    }

    //Post product
    @PostMapping("/store")
    @CrossOrigin(origins = "http://localhost:4200")
    public Store addStore(@RequestBody Store store) {

        return restTemplate.postForObject("http://" + storeServiceBaseUrl + "/store",
                store, Store.class);
    }

    //Update specific product from specific store
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/product/{articleNumber}/store/{storeName}")
    public Store updateStore(@PathVariable String articleNumber, @PathVariable String storeName, @RequestBody Store updatedStore) {

        ResponseEntity<Store> responseEntityStore =
                restTemplate.exchange("http://" + storeServiceBaseUrl + "/product/" + articleNumber + "/store/" + storeName,
                        HttpMethod.PUT, new HttpEntity<>(updatedStore), Store.class);

        return responseEntityStore.getBody();
    }

    //Delete specific product from specific store
    @CrossOrigin(origins = "http://localhost:4200")
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
