package com.marek.api.store.services;

import com.marek.api.store.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findProductByName(String name);

    List<Product>findProductByProducer(String producer);

    @Query("select p from Product p where p.price <= :price and p.producer = :producer")
    List<Product> producedByWithPriceMax(Double price, String producer);
}
