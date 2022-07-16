package com.spring.asignmentspringboot.repository;

import com.spring.asignmentspringboot.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
}
