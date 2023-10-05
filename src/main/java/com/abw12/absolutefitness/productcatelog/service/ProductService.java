package com.abw12.absolutefitness.productcatelog.service;

import com.abw12.absolutefitness.productcatelog.dto.ProductDTO;
import com.abw12.absolutefitness.productcatelog.entity.Product;
import com.abw12.absolutefitness.productcatelog.mappers.GenericMapper;
import com.abw12.absolutefitness.productcatelog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GenericMapper<ProductDTO,Product> mapProductDTOToProduct;
    @Autowired
    private GenericMapper<Product,ProductDTO> mapProductToProductDTO;
    public ProductDTO getProductById(Long productId) {
        return mapProductToProductDTO.mapTo(productRepository.getProductById(productId).orElseThrow(() ->
                new RuntimeException(String.format("Cannot find product by Id %s",productId))),ProductDTO.class);
    }

    public ProductDTO getProductByName(String productName) {
        return mapProductToProductDTO.mapTo(productRepository.getProductByName(productName),ProductDTO.class);
    }

    public List<ProductDTO> getProductByCategoryId(Long catLogID) {
        return mapProductToProductDTO.mapEntityToDTO(productRepository.getProductByCategoryId(catLogID),ProductDTO.class);

    }

    public List<ProductDTO> getProductByCategoryName(String catLogName) {
        return mapProductToProductDTO.mapEntityToDTO(productRepository.getProductByCategoryName(catLogName),ProductDTO.class);

    }
    public List<ProductDTO> getAllProducts(){

        return mapProductToProductDTO.mapEntityToDTO(productRepository.findAll(),ProductDTO.class);
    }

    public ProductDTO saveProduct(ProductDTO productDTO){
        Product product =  mapProductDTOToProduct.mapTo(productDTO,Product.class);
        Product response = productRepository.save(product);
        return mapProductToProductDTO.mapTo(response,ProductDTO.class);
    }

    public ProductDTO updateProduct(ProductDTO productDTO){
        Product product =  mapProductDTOToProduct.mapTo(productDTO,Product.class);
        Product storedProduct = productRepository.getProductById(product.getProductId()).orElseThrow(() ->
                new RuntimeException(String.format("Cannot find product by Id %s",product.getProductId())));
        //below line is also doing the same thing using the mongoTemplate query
//        Product storedProduct =mongoTemplate.findOne(Query.query(Criteria.where("productId").is(productDTO.getProductId())), Product.class);
        storedProduct.setProductId(productDTO.getProductId());
        storedProduct.setBrand(product.getBrand());
        storedProduct.setProductDescription(productDTO.getProductDescription());
        storedProduct.setProductName(productDTO.getProductName());

        Product updatedProduct = productRepository.save(storedProduct);
        return mapProductToProductDTO.mapTo(updatedProduct,ProductDTO.class);
    }
}
