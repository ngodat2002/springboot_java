package com.spring.asignmentspringboot.api;

import com.spring.asignmentspringboot.entity.CartItem;
import com.spring.asignmentspringboot.entity.CartItemId;
import com.spring.asignmentspringboot.entity.Product;
import com.spring.asignmentspringboot.entity.ShoppingCart;
import com.spring.asignmentspringboot.entity.dto.CartItemDTO;
import com.spring.asignmentspringboot.entity.dto.ShoppingCartDTO;
import com.spring.asignmentspringboot.repository.ProductRepository;
import com.spring.asignmentspringboot.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1")
public class ShoppingCartApi {

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.POST, path = "add-to-cart")
    public void saveCart(@RequestParam String userId, @RequestBody ShoppingCartDTO shoppingCartDTO){
        boolean hasException = false;
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .shipName(shoppingCartDTO.getShipName())
                .shipAddress(shoppingCartDTO.getShipAddress())
                .shipNote(shoppingCartDTO.getShipNote())
                .shipPhone(shoppingCartDTO.getShipPhone())
                .isShoppingCart(true)
                .build();
        Set<CartItem> setCartItem = new HashSet<>();
        System.out.println(shoppingCart.getId());
        for (CartItemDTO cartItemDTO :
                shoppingCartDTO.getItems()) {
            Optional<Product> optionalProduct = productRepository.findById(cartItemDTO.getProductId());
            if(!optionalProduct.isPresent()){
                hasException = true;
                break;
            }
            Product product = optionalProduct.get();
            CartItem cartItem = CartItem.builder()
                    .id(new CartItemId(shoppingCart.getId(), product.getId()))
                    .productName(product.getName())
                    .productImage(product.getThumbnails())
                    .quantity(cartItemDTO.getQuantity())
                    .unitPrice(product.getPrice())
                    .shoppingCart(shoppingCart)
                    .build();

            shoppingCart.addTotalPrice(cartItem); // add t???ng gi?? bigdecimal
            setCartItem.add(cartItem);
        }
        shoppingCart.setItems(setCartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @RequestMapping(method = RequestMethod.POST, path = "orders")
    public void order(@RequestParam String shoppingCartId){
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCart.isPresent()){
            ShoppingCart shoppingCart1 = shoppingCart.get();
            shoppingCart1.setIsShoppingCart(false);
            shoppingCartRepository.save(shoppingCart1);
        }
    }
}
