package com.practice.ecom_proj.Repo;


import com.practice.ecom_proj.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
}

