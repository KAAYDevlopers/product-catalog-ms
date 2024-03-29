package com.abw12.absolutefitness.productcatalog.repository;

import com.abw12.absolutefitness.productcatalog.entity.ProductInventoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventoryDAO,String> {

    @Query("SELECT i FROM ProductInventoryDAO i WHERE i.variantId = :variantId")
    Optional<ProductInventoryDAO> getVariantDataById(@Param("variantId") String variantId);

    @Modifying
    @Query("DELETE FROM ProductInventoryDAO i WHERE i.variantId IN :variantIdList")
    Optional<Integer> deleteVariantInventoryByVariantId(List<String> variantIdList);

}
