package com.spring.asignmentspringboot.repository;

import com.spring.asignmentspringboot.entity.Order;
import com.spring.asignmentspringboot.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    Page<Product> findByNameContaining(Pageable pageable, String name);
}
