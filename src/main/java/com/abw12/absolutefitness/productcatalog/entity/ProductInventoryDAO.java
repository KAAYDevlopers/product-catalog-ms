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
@Table(name="productinventory")
public class ProductInventoryDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_inventory_id")
    private Long productInventoryId;
    @Column(name="variant_id")
    private Long variantId;
    @Column(name="quantity")
    private Long quantity;
    private String sku;
    @Column(name = "in_stock_status")
    private Boolean inStockStatus;
    @Column(name="created_at")
    private OffsetDateTime createdAt;
    @Column(name="modified_at")
    private OffsetDateTime modifiedAt;

}
