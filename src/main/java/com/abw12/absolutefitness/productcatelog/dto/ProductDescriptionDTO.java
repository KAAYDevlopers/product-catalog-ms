package com.abw12.absolutefitness.productcatelog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDescriptionDTO  {

    private String about;
    private String benefits;
    private Map<String,Double> nutritionalFacts;
    private List<String> Ingredients;
    private String usage;
    private String manufacturerDetails;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date expiryDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date mfdDate;
    private Date createdDate;
    private Date modifiedDate;

}
