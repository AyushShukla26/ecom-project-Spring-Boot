package com.practice.ecom_proj.Controller;


import com.practice.ecom_proj.Model.Product;
import com.practice.ecom_proj.Services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductServices service;

    @RequestMapping("/products")
    public List<Product> getAllProducts(){
        return service.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id) {
        return service.getElementByID(id);
    }

    @PostMapping(value = "/product", consumes = "multipart/form-data")
    public ResponseEntity<?> addProduct(
            @RequestPart("product") String productJson,
            @RequestPart("imageFile") MultipartFile imageFile) {

        System.out.println("🔥 RAW JSON = " + productJson);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Product product = mapper.readValue(productJson, Product.class);

            System.out.println("🔥 PARSED PRODUCT = " + product);

            Product saved = service.addProduct(product, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductID(@PathVariable int productId){

        Product product= service.getElementByID(productId);
        byte[] imageFile =product.getImageDate();

        return ResponseEntity.ok().
                contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
    }

    @GetMapping({"/products/search"})
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        List<Product> products = this.service.searchProducts(keyword);
        return new ResponseEntity(products, HttpStatus.OK);
    }

}