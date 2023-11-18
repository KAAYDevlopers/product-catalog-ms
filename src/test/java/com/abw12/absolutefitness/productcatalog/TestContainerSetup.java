package com.abw12.absolutefitness.productcatalog;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class TestContainerSetup {

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("productcatalog")
            .withUsername("admin")
            .withPassword("password");
    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry){
        // Set spring.datasource properties to use the dynamic port of the PostgreSQL container
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }
    @BeforeAll
    static void startContainer(){
        postgresContainer.start();
    }
    @AfterAll
    static void stopContainer(){
        postgresContainer.stop();
    }

}
