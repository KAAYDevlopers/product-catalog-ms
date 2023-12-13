package com.abw12.absolutefitness.productcatalog.advice;

public class InvalidDataRequestException extends RuntimeException{
    public InvalidDataRequestException(String errorMsg ){
        super(errorMsg);
    }

}
