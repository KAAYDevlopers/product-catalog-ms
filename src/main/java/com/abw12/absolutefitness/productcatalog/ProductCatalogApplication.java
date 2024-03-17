package com.abw12.absolutefitness.productcatalog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@OpenAPIDefinition(info = @Info(title = "Product-Catalog-MS",
description = "Handle product and product inventory CRUD operations example for fetching product details,listing different types of products and keep track of product inventory",
version = "3.0"))
@EnableTransactionManagement
@EnableFeignClients(basePackages = "com.abw12.absolutefitness.productcatalog.gateway.interfaces")
public class ProductCatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCatalogApplication.class, args);
	}
}
