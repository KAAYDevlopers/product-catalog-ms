package com.abw12.absolutefitness.productcatelog.mappers;

import com.abw12.absolutefitness.productcatelog.dto.ProductDTO;
import com.abw12.absolutefitness.productcatelog.entity.ProductDAO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDAO DtoToEntity(ProductDTO productDTO);
    ProductDTO entityToDto(ProductDAO productDAO);

}


