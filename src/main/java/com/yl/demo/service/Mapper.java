package com.yl.demo.service;

import com.yl.demo.db.ProductEntity;
import com.yl.demo.web.ProductDto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Mapper {

    public static ProductEntity dtoToNewEntity(ProductDto product) {
        return ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .category(product.getCategory())
                .tags(String.join(",", product.getTags()))
                .createdAt(new Date())
                .build();
    }

    public static ProductDto entityToDto(ProductEntity product) {
        String[] tagsArray = product.getTags() != null ? product.getTags().split(",") : null;
        List<String> tags = tagsArray != null ? Arrays.asList(tagsArray) : null;
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .tags(tags)
                .category(product.getCategory())
                .created_at(product.getCreatedAt())
                .build();
    }
}
