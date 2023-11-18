package com.abw12.absolutefitness.productcatalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="product")
public class ProductDAO {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "brand_name")
    private String brandName;
    @Column(name= "category_id")
    private  Long categoryId;
//    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL)
//    private List<ProductVariantDAO> productVariants;
    @Column(name = "product_created_at")
    private OffsetDateTime productCreatedAt;
    @Column(name = "product_modified_at")
    private OffsetDateTime productModifiedAt;
}