package com.abw12.absolutefitness.productcatalog.mappers;

import com.abw12.absolutefitness.productcatalog.dto.ProductCategoryDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductCategoryDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);
    ProductCategoryDAO DtoToEntity(ProductCategoryDTO productCategoryDTO);
    ProductCategoryDTO entityToDto(ProductCategoryDAO productCategoryDAO);
}
