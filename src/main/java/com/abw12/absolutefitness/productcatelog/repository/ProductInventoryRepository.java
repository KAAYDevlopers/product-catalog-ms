package com.abw12.absolutefitness.productcatelog.repository;

import com.abw12.absolutefitness.productcatelog.entity.ProductInventoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventoryDAO,Long> {

    @Query("SELECT i FROM ProductInventoryDAO i WHERE i.variantId = :variantId")
    Optional<ProductInventoryDAO> getVariantDataById(@Param("variantId") Long variantId);
}
