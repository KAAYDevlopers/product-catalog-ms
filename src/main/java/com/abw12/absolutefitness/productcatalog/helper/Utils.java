package com.abw12.absolutefitness.productcatalog.helper;

import java.time.format.DateTimeFormatter;

public class Utils {

    public static DateTimeFormatter dateFormat(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    }
}
