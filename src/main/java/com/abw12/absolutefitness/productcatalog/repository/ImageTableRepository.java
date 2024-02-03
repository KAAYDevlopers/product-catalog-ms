package com.abw12.absolutefitness.productcatalog.repository;

import com.abw12.absolutefitness.productcatalog.entity.VariantImagesDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageTableRepository extends JpaRepository<VariantImagesDAO,String> {
    @Query("SELECT i FROM VariantImagesDAO i where i.variantId =:variantId")
    Optional<List<VariantImagesDAO>> getImageDataByVariantId(String variantId);
}
