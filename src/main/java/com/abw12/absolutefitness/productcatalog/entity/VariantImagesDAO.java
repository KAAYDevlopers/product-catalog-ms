package com.abw12.absolutefitness.productcatalog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productvariantimages",schema = "productcatalog")
public class VariantImagesDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "image_id")
    private String imageId; //primary key
    @Column(name = "product_id")
    private String productId;
    @Column(name = "variant_id")
    private String variantId; //foreign key to the productvariant table
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_size")
    private String fileSize;
    @Column(name = "content_type")
    private String contentType;

}
