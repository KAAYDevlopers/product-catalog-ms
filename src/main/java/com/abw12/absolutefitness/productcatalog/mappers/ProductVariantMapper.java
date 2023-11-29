package com.abw12.absolutefitness.productcatalog.mappers;

import com.abw12.absolutefitness.productcatalog.dto.ProductVariantDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductVariantDAO;
import com.abw12.absolutefitness.productcatalog.helper.OffsetDateTimeParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface ProductVariantMapper extends OffsetDateTimeParser {
    ProductVariantMapper INSTANCE = Mappers.getMapper(ProductVariantMapper.class);

    @Mapping(source = "mfdDate", target = "mfdDate", qualifiedByName = "stringToOffsetDateTime")
    @Mapping(source = "expiryDate", target = "expiryDate", qualifiedByName = "stringToOffsetDateTime")
    @Mapping(source = "variantCreatedAt", target = "variantCreatedAt", qualifiedByName = "stringToOffsetDateTime")
    @Mapping(source = "variantModifiedAt", target = "variantModifiedAt", qualifiedByName = "stringToOffsetDateTime")
    ProductVariantDAO DtoToEntity(ProductVariantDTO productVariantDTO);
    @Mapping(source = "mfdDate", target = "mfdDate", qualifiedByName = "offsetDateTimeToString")
    @Mapping(source = "expiryDate", target = "expiryDate", qualifiedByName = "offsetDateTimeToString")
    @Mapping(source = "variantCreatedAt", target = "variantCreatedAt", qualifiedByName = "offsetDateTimeToString")
    @Mapping(source = "variantModifiedAt", target = "variantModifiedAt", qualifiedByName = "offsetDateTimeToString")
    ProductVariantDTO entityToDto(ProductVariantDAO productVariantDAO);
}
