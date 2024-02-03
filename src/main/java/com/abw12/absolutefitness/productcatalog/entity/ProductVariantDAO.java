package com.abw12.absolutefitness.productcatalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productvariant" ,schema = "productcatalog")
public class ProductVariantDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "variant_id")
    private String variantId;
    @Column(name="product_id")
    private String productId;
    @Column(name = "variant_name")
    private String variantName;
    @Column(name = "variant_value")
    private String variantValue;
    private String about;
    private String benefits;
    @Column(name = "nutrition_facts")
    private String nutritionFacts;
    @Column(name = "usage_dose")
    private String usageDose;
    @Column(name = "manufacturer_details")
    private String manufacturerDetails;
    @Column(name = "number_of_servings")
    private Integer numberOfServings;
    @Column(name = "variant_type")
    private String variantType;
    @Column(name="buy_price")
    private BigDecimal buyPrice;
    @Column(name="on_sale_price")
    private BigDecimal onSalePrice;
    @Column(name="mfd_date")
    private OffsetDateTime mfdDate;
    @Column(name="expiry_date")
    private OffsetDateTime expiryDate;
    @Column(name="variant_created_at")
    private OffsetDateTime variantCreatedAt;
    @Column(name="variant_modified_at")
    private OffsetDateTime variantModifiedAt;


}
