package com.abw12.absolutefitness.productcatelog.mappers;

import com.abw12.absolutefitness.productcatelog.dto.ProductVariantDTO;
import com.abw12.absolutefitness.productcatelog.entity.ProductVariantDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface ProductVariantMapper {
    ProductVariantMapper INSTANCE = Mappers.getMapper(ProductVariantMapper.class);
    ProductVariantDTO entityToDto(ProductVariantDAO productVariantDAO);
    ProductVariantDAO DtoToEntity(ProductVariantDTO productVariantDTO);
}
