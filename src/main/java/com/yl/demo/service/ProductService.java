package com.yl.demo.service;

import com.yl.demo.db.ProductEntity;
import com.yl.demo.db.ProductRepository;
import com.yl.demo.web.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    /**
     * Insert new project to repository.
     *
     * @param product
     * @return
     */
    public ProductDto insertProduct(ProductDto product) {
        ProductEntity entity = Mapper.dtoToNewEntity(product);
        entity = repo.save(entity);
        return Mapper.entityToDto(entity);
    }

    /**
     * Search of products by category, using an exact match on the text of the ‘category’ field,
     * supporting pagination by page number and max entries per page.
     * This will return the list of products matching the criteria sorted by created_at from newest to oldest.
     */
    public List<ProductDto> findByCategory(String category, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        List<ProductEntity> list = repo.findByCategory(category, pageable);
        return list.stream().map(p -> Mapper.entityToDto(p))
                .collect(Collectors.toList());
    }

}
