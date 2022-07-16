package com.example.springbootassignment.api;

import com.example.springbootassignment.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "api/v1/carts")
@RestController
@CrossOrigin("*")
@Slf4j
public class ShoppingCartApi {
    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> show(@Param("userId") int userId){
        log.info("UserId: " + userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findShoppingCartByUserId(userId)) ;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/add")
    public ResponseEntity<?> show(@Param("userId") int userId, @Param("productId") String productId, @Param("quantity") int quantity){
        orderService.addShoppingCart(userId, productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findShoppingCartByUserId(userId)) ;
    }
}
