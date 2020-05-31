package com.yl.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yl.demo.service.ProductService;
import com.yl.demo.web.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class WebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    @Test
    public void shouldReturnEmptyList() throws Exception {

        when(service.findByCategory("", 0, 2)).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get("/v1/products?category=q")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    public void createProduct() throws Exception {
        ProductDto product1 = ProductDto.builder()
                .name("Red Shirt")
                .description("Red hugo boss shirt")
                .brand("Hugo Boss")
                .tags(Arrays.asList(new String[]{"red", "shirt", "slim fit"}))
                .category("apparel")
                .build();
        ProductDto result = ProductDto.builder()
                .id(UUID.randomUUID())
                .name("Red Shirt")
                .description("Red hugo boss shirt")
                .brand("Hugo Boss")
                .tags(Arrays.asList(new String[]{"red", "shirt", "slim fit"}))
                .category("apparel")
                .created_at(new Date())
                .build();

        when(service.insertProduct(product1)).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/products")
                .content(asJsonString(product1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("created_at")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_at").exists())
        ;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}