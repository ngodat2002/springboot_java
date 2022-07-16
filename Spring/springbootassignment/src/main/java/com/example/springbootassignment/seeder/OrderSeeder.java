package com.example.springbootassignment.seeder;

import com.example.springbootassignment.entity.*;
import com.example.springbootassignment.entity.myenum.OrderSimpleStatus;
import com.example.springbootassignment.repository.OrderRepository;
import com.example.springbootassignment.util.NumberUtil;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class OrderSeeder {
    @Autowired
    OrderRepository orderRepository;
    public static List<Order> orders = new ArrayList<>();
    public static final int NUMBER_OF_ORDER = 100;
    public static final int NUMBER_OF_DONE = 60;
    public static final int NUMBER_OF_CANCEL = 20;
    public static final int NUMBER_OF_PENDING = 20;
    public static final int MIN_ORDER_DETAIL = 2;
    public static final int MAX_ORDER_DETAIL = 5;
    public static final int MIN_PRODUCT_QUANTITY = 1;
    public static final int MAX_PRODUCT_QUANTITY = 5;

    public static final int MIN_ACCOUNT = 0;
    public static final int MAX_ACCOUNT = 10;
    public void generate(){
        log.debug("------------Seeding order-------------");
        Faker faker = new Faker();
        for (int i = 0; i < NUMBER_OF_ORDER; i++) {
            int UserIndex = NumberUtil.getRandomNumber(MIN_ACCOUNT, MAX_ACCOUNT);
            Account account = AccountSeeder.accountList.get(UserIndex);
            // Tạo mới đơn hàng.
            Order order = new Order();
            order.setId(UUID.randomUUID().toString());
            order.setShoppingCart(false);
            order.setAccount(account);
            order.setStatus(OrderSimpleStatus.DONE);
            // Tạo danh sách order detail cho đơn hàng.
            Set<OrderDetail> orderDetails = new HashSet<>();
            // map này dùng để check sự tồn tại của sản phẩm trong order detail.
            HashMap<String, Product> mapProduct = new HashMap<>();
            // generate số lượng của order detail.
            int orderDetailNumber = NumberUtil.getRandomNumber(MIN_ORDER_DETAIL, MAX_ORDER_DETAIL);
            for (int j = 0; j < orderDetailNumber; j++) {
                // lấy random product.
                int randomProductIndex = NumberUtil.getRandomNumber(0, ProductSeeder.products.size() - 1);
                Product product = ProductSeeder.products.get(randomProductIndex);
                // check tồn tại
                if (mapProduct.containsKey(product.getId())) {
                    continue; // bỏ qua hoặc cộng dồn
                }
                // tạo order detail theo sản phẩm random
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(new OrderDetailId(order.getId(), product.getId()));
                orderDetail.setOrder(order); // set quan hệ
                orderDetail.setProduct(product); // set quan hệ
                orderDetail.setUnitPrice(product.getPrice()); // giá theo sản phẩm

                // random số lượng theo cấu hình
                orderDetail.setQuantity(NumberUtil.getRandomNumber(MIN_PRODUCT_QUANTITY, MAX_PRODUCT_QUANTITY));
                // đưa vào danh sách order detail
                orderDetails.add(orderDetail);
                mapProduct.put(product.getId(), product);
            }
            // set quan hệ với order
            order.setOrderDetails(orderDetails);
            order.calculateTotalPrice();
            orders.add(order);
        }
        orderRepository.saveAll(orders);
        log.debug("------------End Seeding order-------------");
    }
}
