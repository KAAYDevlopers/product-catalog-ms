package com.abw12.absolutefitness.productcatelog.mappers;

import com.abw12.absolutefitness.productcatelog.dto.ProductCategoryDTO;
import com.abw12.absolutefitness.productcatelog.entity.ProductCategoryDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);
    ProductCategoryDAO DtoToEntity(ProductCategoryDTO productCategoryDTO);
    ProductCategoryDTO entityToDto(ProductCategoryDAO productCategoryDAO);
}
