package com.abw12.absolutefitness.productcatalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="productcategory" ,schema = "productcatalog")
public class ProductCategoryDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_category_id")
    private String productCategoryId;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name="category_description")
    private String categoryDescription;
}
