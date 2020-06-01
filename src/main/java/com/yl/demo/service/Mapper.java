package com.yl.demo.service;

import com.yl.demo.db.ProductEntity;
import com.yl.demo.web.ProductDto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Simple mapper of ProductDto to ProductEntity and vice versa.
 */
public class Mapper {

    public static ProductEntity dtoToNewEntity(ProductDto product) {
        // tags list stored as comma separated list of tags
        String tags = product.getTags() != null ? String.join(",", product.getTags()) : null;
        return ProductEntity.builder()
                .name(product.getName())
                .description(product.getDescription())
                .brand(product.getBrand())
                .category(product.getCategory())
                .tags(tags)
                .createdAt(new Date()) // overrides createdAt
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
