package com.yl.demo.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * ProductDto for JSON requests and responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="product")
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    private String brand;
    private String tags; // tags list stored as comma separated list of tags
    private String category;
    private Date createdAt;

}
