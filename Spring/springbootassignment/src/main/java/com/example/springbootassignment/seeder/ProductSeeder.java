package com.example.springbootassignment.seeder;

import com.example.springbootassignment.entity.Category;
import com.example.springbootassignment.entity.Product;
import com.example.springbootassignment.entity.myenum.ProductStatus;
import com.example.springbootassignment.repository.ProductRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Component
public class ProductSeeder {
    @Autowired
    ProductRepository productRepository;
    Faker faker = new Faker();
    public static List<Product> products = new ArrayList<>();
    public static final int NUMBER_OF_PRODUCT = 50;
    public void generate(){
        for (int i = 0; i < NUMBER_OF_PRODUCT; i++){
            int randomCateIndex = faker.number().numberBetween(0, CategorySeeder.categoryList.size() -1);
            Category category = CategorySeeder.categoryList.get(randomCateIndex);
            Product product = new Product();
            product.setId(UUID.randomUUID().toString() + i);
            product.setName(String.valueOf(faker.leagueOfLegends()));
            product.setDescription(faker.lorem().sentence());
            product.setDetail(faker.lorem().sentence());
            product.setThumbnails(faker.avatar().image());
            product.setStatus(ProductStatus.ACTIVE);
            product.setPrice(new BigDecimal(faker.number().numberBetween(10, 200) * 10000));
            product.setCategory(category);
            products.add(product);
        }
        productRepository.saveAll(products);
    }
}
