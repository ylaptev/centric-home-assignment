package com.yl.demo;

import com.yl.demo.web.ProductDto;

import java.util.Arrays;

// TestData Helper
public class TestData {
    private TestData() {}

    final static String[][] TEST_DATA = {
            // 0:name, 1:description, 2:brand, 3:category, 4: tags
            { "Red Shirt", "Red hugo boss shirt", "Hugo Boss", "apparel", "red,shirt,slim fit" },
            { "Blue Shirt", "Blue hugo boss shirt", "Hugo Boss", "apparel", "blue,shirt" },
            { "White Shirt", "White hugo boss shirt", "Hugo Boss", "apparel", "white,shirt,regular fit" },
            { "Orange Shirt", "Orange Tommy shirt", "Tommy", "apparel", "orange,shirt,regular fit" },
            { "Black Shirt", "Black Tommy shirt", "Tommy", "apparel", "black,shirt,slim fit" },
            { "Green Shirt", "Green Tommy shirt", "Tommy", "apparel", "white,shirt" },
    };

    final static ProductDto buildTestProductDto(int i) {
        return buildTestProductDto(TEST_DATA[i % TEST_DATA.length]);
    }

    final static ProductDto buildTestProductDto(String[] testData) {
        return ProductDto.builder()
                .name(testData[0])
                .description(testData[1])
                .brand(testData[2])
                .category(testData[3])
                .tags(Arrays.asList(testData[4].split(",")))
                .build();
    }
}
