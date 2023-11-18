package com.abw12.absolutefitness.productcatalog.mappers;

import com.abw12.absolutefitness.productcatalog.dto.ProductInventoryDTO;
import com.abw12.absolutefitness.productcatalog.entity.ProductInventoryDAO;
import com.abw12.absolutefitness.productcatalog.helper.DateTimeParser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductInventoryMapper extends DateTimeParser {

    ProductInventoryMapper INSTANCE = Mappers.getMapper(ProductInventoryMapper.class);

    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "stringToOffsetDateTime")
    @Mapping(source = "modifiedAt", target = "modifiedAt", qualifiedByName = "stringToOffsetDateTime")
    ProductInventoryDAO DtoToEntity(ProductInventoryDTO productInventoryDTO);
    //    @InheritInverseConfiguration
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "offsetDateTimeToString")
    @Mapping(source = "modifiedAt", target = "modifiedAt", qualifiedByName = "offsetDateTimeToString")
    ProductInventoryDTO entityToDto(ProductInventoryDAO productInventoryDAO);
}
