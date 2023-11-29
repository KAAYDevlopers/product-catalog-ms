package com.abw12.absolutefitness.productcatalog.mappers;

import com.abw12.absolutefitness.productcatalog.dto.ProductDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductDAO;
import com.abw12.absolutefitness.productcatalog.helper.OffsetDateTimeParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper extends OffsetDateTimeParser {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "productCreatedAt", target = "productCreatedAt", qualifiedByName = "stringToOffsetDateTime")
    @Mapping(source = "productModifiedAt", target = "productModifiedAt", qualifiedByName = "stringToOffsetDateTime")
    ProductDAO DtoToEntity(ProductDTO productDTO);
    @Mapping(source = "productCreatedAt", target = "productCreatedAt", qualifiedByName = "offsetDateTimeToString")
    @Mapping(source = "productModifiedAt", target = "productModifiedAt", qualifiedByName = "offsetDateTimeToString")
    ProductDTO entityToDto(ProductDAO productDAO);

}


