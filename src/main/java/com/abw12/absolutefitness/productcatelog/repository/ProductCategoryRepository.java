package com.abw12.absolutefitness.productcatelog.repository;

import com.abw12.absolutefitness.productcatelog.entity.ProductCategoryDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryDAO,Long> {

    @Query("SELECT c FROM ProductCategoryDAO c WHERE c.productCategoryId = :categoryId")
    Optional<ProductCategoryDAO> getProductCategory(Long categoryId);

    @Query("SELECT c FROM ProductCategoryDAO c WHERE c.categoryName = :categoryName")
    Optional<ProductCategoryDAO> getProductCategoryByName(String categoryName);
}
