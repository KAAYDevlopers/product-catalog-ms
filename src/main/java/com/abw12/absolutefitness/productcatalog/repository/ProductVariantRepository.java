package com.abw12.absolutefitness.productcatalog.repository;

import com.abw12.absolutefitness.productcatalog.dto.ProductFiltersDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductVariantDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository  extends JpaRepository<ProductVariantDAO,String> {

    @Query("SELECT v FROM ProductVariantDAO v WHERE v.productId =:productId")
    Optional<List<ProductVariantDAO>> getVariantsByProductId(@Param("productId") String productId);
    @Query("SELECT v FROM ProductVariantDAO v WHERE " +
            "(v.productId =:productId) AND "+
            "(:#{#filters.variantName} is null or v.variantName IN :#{#filters.variantName}) AND" +
            "(:#{#filters.variantValue} is null or v.variantValue IN :#{#filters.variantValue}) AND" +
            "(:#{#filters.numberOfServings} is null or v.numberOfServings = :#{#filters.numberOfServings}) AND" +
            "(:#{#filters.variantType} is null or v.variantType = :#{#filters.variantType}) AND" +
            "(:#{#filters.minOnSalePrice} is null or v.onSalePrice >= :#{#filters.minOnSalePrice}) AND" +
            "(:#{#filters.maxOnSalePrice} is null or v.onSalePrice <= :#{#filters.maxOnSalePrice})")
    List<ProductVariantDAO> getVariantsByFilters(@Param("productId") String productId ,  @Param("filters") ProductFiltersDTO filters);
    @Query("SELECT v FROM ProductVariantDAO v WHERE v.variantId =:variantId")
    Optional<ProductVariantDAO> getVariantData(@Param("variantId") String variantId);

    @Modifying
    @Query("DELETE FROM ProductVariantDAO p WHERE p.productId =:productId")
    Optional<Integer> deleteVariantsByProductId(String productId);

    @Modifying
    @Query("DELETE FROM ProductVariantDAO p WHERE p.variantId IN :variantIdList")
    Optional<Integer> deleteVariantsInVariantIdList(List<String> variantIdList);
}
