package com.example.springbootassignment.repository;

import com.example.springbootassignment.entity.Product;
import com.example.springbootassignment.entity.myenum.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findAllByStatusEquals(ProductStatus status, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from products")
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByNameContains(String search, Pageable pageable);

    Page<Product> findAllByPriceEquals(BigDecimal price, Pageable pageable);

    Page<Product> findAllByCategory_Name(String search, Pageable pageable);
    Page<Product> findAllByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);
}
