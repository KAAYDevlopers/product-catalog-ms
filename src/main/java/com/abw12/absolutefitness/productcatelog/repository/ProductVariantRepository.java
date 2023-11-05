package com.abw12.absolutefitness.productcatelog.repository;

import com.abw12.absolutefitness.productcatelog.entity.ProductVariantDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository  extends JpaRepository<ProductVariantDAO,Long> {

    @Query("SELECT p FROM ProductVariantDAO p WHERE p.productId =:productId")
    List<ProductVariantDAO> getVariantsByProductId(@Param("productId") Long productId);
}
