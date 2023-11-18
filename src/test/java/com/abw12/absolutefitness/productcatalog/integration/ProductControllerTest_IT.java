package com.abw12.absolutefitness.productcatalog.integration;

import com.abw12.absolutefitness.productcatalog.TestContainerSetup;
import com.abw12.absolutefitness.productcatalog.dto.ProductDTO;
import com.abw12.absolutefitness.productcatalog.helper.JsonFileReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class ProductControllerTest_IT extends TestContainerSetup {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonFileReader fileReader;

//    @BeforeEach
//    public void setup(){
//        this.mockMvc = MockMvcBuilders.standaloneSetup(ProductController.class).build();
//    }

    static String basePathDirectory = "src/test/java/com/abw12/absolutefitness/productcatalog/testdatafiles/";
    static String basePathAPI = "/catalog/product";
    @Test
    public void insertProductTest() throws Exception {
        //get ProductDto as input from test jsonFiles
        ProductDTO inputData = fileReader.readJsonData(basePathDirectory + "insertproductdata.json", ProductDTO.class);

        //call controller endpoints
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post(basePathAPI + "/insertProduct")
                        .contentType("application/json")
                        .content(Objects.requireNonNull(asJsonString(inputData)))
                        .accept("application/json")
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").exists());
    }

    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
