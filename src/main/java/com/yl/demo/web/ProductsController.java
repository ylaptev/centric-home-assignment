package com.yl.demo.web;

import com.yl.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/v1/products")
public class ProductsController {

    @Autowired
    private ProductService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductDto getProducts(@Valid @RequestBody ProductDto product) {
        log.info("Creating Input: {}", product);
        return service.insertProduct(product);
    }

    /**
     * Returns product list with pagination, first page = 0
     *
     * @param category
     * @param page page number, default 0
     * @param pageSize page size, default 5
     * @return
     */
    @GetMapping
    public Collection<ProductDto> getProducts(@RequestParam(required = false) String category,
                                              @RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "5") int pageSize) {
        return service.findByCategory(category, page, pageSize);
    }

/*
    @GetMapping(path = "/err")
    public String getError() {
        if (true) {
            throw new RuntimeException("Fake error.");
        }
        return "OK";
    }
*/

}
