package com.yl.demo.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, UUID> {

    List<ProductEntity> findByCategory(String category, Pageable pageable);

}
