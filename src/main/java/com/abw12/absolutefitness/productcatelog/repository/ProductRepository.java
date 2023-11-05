package com.abw12.absolutefitness.productcatelog.repository;

import com.abw12.absolutefitness.productcatelog.entity.ProductDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductDAO,Long> {

//    @Query("SELECT p,v FROM ProductDAO p LEFT JOIN ProductVariantDAO v ON p.productId = v.productId WHERE p.productId = :productId")
//    List<Object[]> getProductById(@Param("productId") Long productId);

//    @Query("SELECT DISTINCT p FROM ProductDAO p LEFT JOIN FETCH p.productVariants WHERE p.productId = :productId")
//    Optional<ProductDAO> getProductById(@Param("productId") Long productId);
//    @Query("SELECT DISTINCT p FROM ProductDAO p LEFT JOIN FETCH p.productVariants WHERE p.productName = :productName")
//    Optional<ProductDAO> getProductByName(@Param("productName") String productName);
    @Query("SELECT p FROM ProductDAO p WHERE p.productId =:productId")
    Optional<ProductDAO> getProductById(Long productId);

    @Query("SELECT p FROM ProductDAO p WHERE p.productName =:productName")
    List<ProductDAO> getProductByName(String productName);

    @Query("Select p FROM ProductDAO p WHERE p.categoryId =:categoryId")
    List<ProductDAO> getProductsByCategoryID(Long categoryId);
//    @Query("{'productName':?0}")
//    ProductDAO getProductByName(String productName);
//
//    @Query("{'catLogID':?0}")
//    List<ProductDAO> getProductByCategoryId(Long catLogId);
//
//    @Query("{'catLogName':?0}")
//    List<ProductDAO> getProductByCategoryName(String catLogName);

}
