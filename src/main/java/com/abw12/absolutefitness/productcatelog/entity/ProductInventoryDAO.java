package com.abw12.absolutefitness.productcatelog.entity;

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
    @Column(name="stock_total")
    private Long stockTotal;
    @Column(name="created_at")
    private OffsetDateTime createdAt;
    @Column(name="modified_at")
    private OffsetDateTime modifiedAt;

}
