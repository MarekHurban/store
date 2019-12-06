package com.marek.api.store.services;

import com.marek.api.store.data.Product;

import java.util.*;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

/**
 * @author Marek Hurban
 *
 * this is obsolete
 */
@Service
public class ProductService {
    List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product findProduct(Predicate<Product> accordingTo) {
        return products.stream().filter(accordingTo).findFirst().orElse(null);
    }

    public Predicate<Product> getProdByName(String name) {
        Predicate<Product> byName = p-> p.getName().equals(name);
        return byName;
    }

    public Predicate<Product> getProdById(int id) {
        Predicate<Product> byId = p-> p.getId() == id;
        return byId;
    }

    public void addProduct(Product product) {
        if (products.size() == 0) {
            product.setId(0);
        } else {
            int nextId = products.get(products.size()).getId() + 1;
            product.setId(nextId);
        }
        products.add(product);
    }

    public boolean deleteProduct(String name) {
        return products.remove(findProduct(getProdByName(name)));
    }
}
