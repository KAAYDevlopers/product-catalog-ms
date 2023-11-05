package com.abw12.absolutefitness.productcatelog.repository;

import com.abw12.absolutefitness.productcatelog.entity.ProductInventoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventoryDAO,Long> {

//    @Query("SELECT v FROM ProductInventoryDAO v WHERE v.variantId = :variantId")
//    Optional<ProductInventoryDAO> getProductById(@Param("variantId") Long variantId);
}
