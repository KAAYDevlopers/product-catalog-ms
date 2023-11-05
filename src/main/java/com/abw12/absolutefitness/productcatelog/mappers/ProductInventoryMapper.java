package com.abw12.absolutefitness.productcatelog.mappers;

import com.abw12.absolutefitness.productcatelog.dto.ProductInventoryDTO;
import com.abw12.absolutefitness.productcatelog.entity.ProductInventoryDAO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductInventoryMapper {

    ProductInventoryMapper INSTANCE = Mappers.getMapper(ProductInventoryMapper.class);

    @Mapping(source = "productInventoryDTO.stockInTotal" , target = "stockTotal")
    ProductInventoryDAO DtoToEntity(ProductInventoryDTO productInventoryDTO);
    @InheritInverseConfiguration
    ProductInventoryDTO entityToDto(ProductInventoryDAO productInventoryDAO);
}
