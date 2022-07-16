package com.example.springbootassignment.seeder;

import com.example.springbootassignment.entity.Category;
import com.example.springbootassignment.repository.CategoryRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CategorySeeder {
    @Autowired
    CategoryRepository categoryRepository;
    Faker faker = new Faker();
    public static List<Category> categoryList = new ArrayList<>();
    public static final int NUMBER_OF_CATEGORY = 10;

    public void generate(){
        for (int i = 0; i < NUMBER_OF_CATEGORY; i++){
            Category category = new Category();
            category.setId(UUID.randomUUID().toString());
            category.setName(faker.leagueOfLegends().rank());
            categoryList.add(category);
        }
        categoryRepository.saveAll(categoryList);
    }
}
