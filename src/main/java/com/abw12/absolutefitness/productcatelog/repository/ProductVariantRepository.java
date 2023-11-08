package com.abw12.absolutefitness.productcatelog.repository;

import com.abw12.absolutefitness.productcatelog.dto.ProductFiltersDTO;
import com.abw12.absolutefitness.productcatelog.entity.ProductVariantDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVariantRepository  extends JpaRepository<ProductVariantDAO,Long> {

    @Query("SELECT v FROM ProductVariantDAO v WHERE v.productId =:productId")
    List<ProductVariantDAO> getVariantsByProductId(@Param("productId") Long productId);
    @Query("SELECT v FROM ProductVariantDAO v WHERE " +
            "(v.productId =:productId) AND "+
            "(:#{#filters.variantName} is null or v.variantName = :#{#filters.variantName}) AND" +
            "(:#{#filters.variantValue} is null or v.variantValue = :#{#filters.variantValue}) AND" +
            "(:#{#filters.numberOfServings} is null or v.numberOfServings = :#{#filters.numberOfServings}) AND" +
            "(:#{#filters.variantType} is null or v.variantType = :#{#filters.variantType}) AND" +
            "(:#{#filters.minOnSalePrice} is null or v.onSalePrice >= :#{#filters.minOnSalePrice}) AND" +
            "(:#{#filters.maxOnSalePrice} is null or v.onSalePrice <= :#{#filters.maxOnSalePrice})")
    List<ProductVariantDAO> getVariantsByFilters(@Param("productId") Long productId ,  @Param("filters") ProductFiltersDTO filters);
}
