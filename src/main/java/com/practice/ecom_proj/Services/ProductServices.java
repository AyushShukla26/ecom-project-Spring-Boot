package com.practice.ecom_proj.Services;


import com.practice.ecom_proj.Model.Product;
import com.practice.ecom_proj.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServices {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    // ProductServices.java
    public Product getElementByID(int id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id: " + id)
                );
    }



    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {

        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());

        return repo.save(product);
    }
}

