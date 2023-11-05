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
@Table(name = "productvariant")
public class ProductVariantDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    private Long variantId;
    @Column(name="product_id")
    private Long productId;
    //    @ManyToOne
//    @JoinColumn(name="product_id")
//    private ProductDAO product;
    @Column(name = "variant_name")
    private String variantName;
    @Column(name = "variant_value")
    private String variantValue;
    private String sku;
    @Column(name = "image_path")
    private String imagePath;
    private String about;
    private String benefits;
    @Column(name = "nutrition_facts")
    private String nutritionFacts;
    @Column(name = "usage_dose")
    private String usageDose;
    @Column(name = "manufacturer_details")
    private String manufacturerDetails;
    @Column(name="buy_price")
    private Double buyPrice;
    @Column(name="on_sale_price")
    private Double onSalePrice;
    @Column(name="mfd_date")
    private OffsetDateTime mfdDate;
    @Column(name="expiry_date")
    private OffsetDateTime expiryDate;
    @Column(name="variant_created_at")
    private OffsetDateTime variantCreatedAt;
    @Column(name="variant_modified_at")
    private OffsetDateTime variantModifiedAt;


}
