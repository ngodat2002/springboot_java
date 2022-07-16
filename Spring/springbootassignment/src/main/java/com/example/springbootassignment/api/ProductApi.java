package com.example.springbootassignment.api;

import com.example.springbootassignment.entity.Product;
import com.example.springbootassignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RequestMapping(path = "api/v1/products")
@RestController
@CrossOrigin("*")
public class ProductApi {
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Product> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "limit", defaultValue = "10") int limit){
        return productService.findAllByActive(page, limit);
    }

    @RequestMapping(value = "search_by_name", method = RequestMethod.POST)
    public Page<Product> SearchByName(@RequestParam(name = "searchStr", defaultValue = "") String search,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "limit", defaultValue = "10") int limit){
        return productService.searchByName(search, page, limit);
    }

    @RequestMapping(value = "search_by_price", method = RequestMethod.POST)
    public Page<Product> SearchByPrice(@RequestParam(name = "price", defaultValue = "0") BigDecimal price,
                                      @RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "limit", defaultValue = "10") int limit){
        return productService.searchByPrice(price, page, limit);
    }

    @RequestMapping(value = "search_by_category", method = RequestMethod.POST)
    public Page<Product> SearchByCategory(@RequestParam(name = "cate", defaultValue = "") String search,
                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                       @RequestParam(name = "limit", defaultValue = "10") int limit){
        return productService.searchByCategoryName(search, page, limit);
    }

    @RequestMapping(value = "search_by_price_between", method = RequestMethod.POST)
    public Page<Product> SearchByPriceBetween(@RequestParam(name = "min", defaultValue = "0") BigDecimal min,
                                              @RequestParam(name = "max", defaultValue = "0") BigDecimal max,
                                          @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "limit", defaultValue = "10") int limit){
        return productService.searchByPriceBetween(min, max, page, limit);
    }
}
