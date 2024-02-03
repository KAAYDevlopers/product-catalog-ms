package com.abw12.absolutefitness.productcatalog.repository;

import com.abw12.absolutefitness.productcatalog.dto.ProductFiltersDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductDAO,String> {

//    @Query("SELECT p,v FROM ProductDAO p LEFT JOIN ProductVariantDAO v ON p.productId = v.productId WHERE p.productId = :productId")
//    List<Object[]> getProductById(@Param("productId") Long productId);

//    @Query("SELECT DISTINCT p FROM ProductDAO p LEFT JOIN FETCH p.productVariants WHERE p.productId = :productId")
//    Optional<ProductDAO> getProductById(@Param("productId") Long productId);
//    @Query("SELECT DISTINCT p FROM ProductDAO p LEFT JOIN FETCH p.productVariants WHERE p.productName = :productName")
//    Optional<ProductDAO> getProductByName(@Param("productName") String productName);
    @Query("SELECT p FROM ProductDAO p WHERE p.productId =:productId")
    Optional<ProductDAO> getProductById(String productId);

    @Query("SELECT p FROM ProductDAO p WHERE p.productName =:productName")
    Optional<List<ProductDAO>> getProductByName(String productName);

    @Query("SELECT p FROM ProductDAO p WHERE p.categoryId =:categoryId")
    Optional<List<ProductDAO>> getProductsByCategoryID(String categoryId);
    @Query("SELECT p FROM ProductDAO p WHERE (p.categoryId =:#{#filters.categoryId}) AND " +
            "(:#{#filters.brandName} is null or p.brandName IN :#{#filters.brandName}) ")
    List<ProductDAO> getProductByFilters(@Param("filters") ProductFiltersDTO filters);

    @Modifying
    @Query("DELETE FROM ProductDAO p WHERE p.productId =:productId")
    Optional<Integer> deleteProductById(String productId);

}
