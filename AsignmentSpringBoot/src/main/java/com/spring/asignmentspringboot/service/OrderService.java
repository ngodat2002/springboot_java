package com.spring.asignmentspringboot.service;

import com.spring.asignmentspringboot.entity.Order;
import com.spring.asignmentspringboot.entity.search.*;
import com.spring.asignmentspringboot.repository.OrderRepository;
import com.spring.asignmentspringboot.util.ConvertDateHelper;
import com.spring.asignmentspringboot.util.LocalDatetimehelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spring.asignmentspringboot.entity.search.SearchCriteriaOperator.*;

@Service
public class OrderService {
    final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    public Map<String, Object> findAll(SearchBody searchBody){
        Specification specification = Specification.where(null);

        if (searchBody.getNameUser() != null && searchBody.getNameUser().length() > 0 ){
            specification = specification.and(new OrderSpecification(new SearchCriteria("fullName", JOIN_USER, searchBody.getNameUser())));
        }
        if (searchBody.getPhone() != null && searchBody.getPhone().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("phone",JOIN_USER, searchBody.getPhone())));
        }
        if (searchBody.getNameProduct() != null && searchBody.getNameProduct().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("name",JOIN_DETAIL_PRODUCT, searchBody.getNameProduct())));
        }
        if (searchBody.getOrderId() != null && searchBody.getOrderId().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("id", LIKE,searchBody.getOrderId())));
        }
        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){
//            log.info("check start: " + orderSearchBody.getStart() );
//            log.info("Check Start begin" + searchBody.getStart());

            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getStart());
//            log.info("Check Start" + date);
//            log.info("check start convert date: " + date );
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", GREATER_THAN_OR_EQUALS,date)));
        }
        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getEnd());
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", LESS_THAN_OR_EQUALS,date)));
        }

        Sort sort= Sort.by(Sort.Order.asc("id"));
        if (searchBody.getSort() !=null && searchBody.getSort().length() >0){
            if (searchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }
        Pageable pageable = PageRequest.of(searchBody.getPage() -1, searchBody.getLimit(),sort );
        Page<Order> pageOrder = orderRepository.findAll(specification,pageable);
        List<Order> orderList = pageOrder.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",orderList);
        responses.put("currentPage",pageOrder.getNumber() + 1);
        responses.put("totalItems",pageOrder.getTotalElements());
        responses.put("totalPage",pageOrder.getTotalPages());
        return responses;
    }

//    public Page<Order> findAll(int page, int limit,
//                               Specification<Order> orderSpecification) {
//        return orderRepository.findAll(
//                orderSpecification, PageRequest.of(page - 1, limit));
//    }
//
//    public Page<Order> findAll(FilterParameter param) {
//        Specification<Order> specification = Specification.where(null);
//        if (param.getKeyword() != null && param.getKeyword().length() > 0) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("keyword", SearchCriteriaOperator.JOIN, param.getKeyword());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        if (param.getStatus() != 0) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("status", SearchCriteriaOperator.EQUALS, param.getStatus());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        if (param.getUserId() != null) {
//            SearchCriteria searchCriteria
//                    = new SearchCriteria("userId", SearchCriteriaOperator.EQUALS, param.getUserId());
//            OrderSpecification filter = new OrderSpecification(searchCriteria);
//            specification = specification.and(filter);
//        }
//        return orderRepository.findAll(
//                specification, PageRequest.of(param.getPage() - 1, param.getLimit()));
    }
