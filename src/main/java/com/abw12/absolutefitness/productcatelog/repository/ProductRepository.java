package com.abw12.absolutefitness.productcatelog.repository;

import com.abw12.absolutefitness.productcatelog.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    @Query("{'productId':?0}")
    Optional<Product> getProductById(Long productId);

    @Query("{'productName':?0}")
    Product getProductByName(String productName);

    @Query("{'catLogID':?0}")
    List<Product> getProductByCategoryId(Long catLogId);

    @Query("{'catLogName':?0}")
    List<Product> getProductByCategoryName(String catLogName);

}
