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
@Table(name="productinventory" ,schema = "productcatalog")
public class ProductInventoryDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "variant_inventory_id")
    private String variantInventoryId;
    @Column(name="variant_id")
    private String variantId;
    @Column(name="quantity")
    private Long quantity;
    private String sku;
    @Column(name="created_at")
    private OffsetDateTime createdAt;
    @Column(name="modified_at")
    private OffsetDateTime modifiedAt;

}
