package com.abw12.absolutefitness.productcatelog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@OpenAPIDefinition(info = @Info(title = "Product-Catalog-MS",
description = "Handle product CRUD operations example for fetching product details,listing different types of products",
version = "3.0"))
@EnableTransactionManagement
public class ProductCatelogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCatelogApplication.class, args);
	}

}
