package com.marek.api.store.controllers;

import com.marek.api.store.data.Product;

import com.marek.api.store.services.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marek Hurban
 *
 */
@RestController
public class ProductRestController {

    private final ProductRepo repo;

    public ProductRestController(ProductRepo repo) {
        this.repo = repo;
    }

    @RequestMapping(value = "/")
    public String home() {
        return "Homepage of spring boot demo project(store).";
    }

    @RequestMapping( {"/products/find", "/products"})
    public Iterable<Product> getAllProducts() {
        ArrayList<Product> products = (ArrayList<Product>) repo.findAll();
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return products;
    }

    @RequestMapping("/products/find/name")
    public List<Product> getProductByName(@RequestParam(value = "name") String name) {
        List<Product> products = repo.findProductByName(name);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return products;
    }

    @RequestMapping("/products/find/producer")
    public List<Product> getProductByProducer(@RequestParam(value = "producer") String producer) {
        List<Product> products = repo.findProductByProducer(producer);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return products;
    }

    @RequestMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Product product = repo.findById(id).orElse(null);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping("products/add")
    public void addProduct(@RequestBody Product newProduct) {
        try {
            repo.save(newProduct);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("products/remove")
    public ResponseEntity<HttpStatus> removeProduct(@RequestParam(name = "id") int id) {
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
