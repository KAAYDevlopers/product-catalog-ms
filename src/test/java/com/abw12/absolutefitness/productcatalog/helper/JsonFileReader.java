package com.abw12.absolutefitness.productcatalog.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;

@Service
public class JsonFileReader {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T readJsonData(String jsonFilePath, Class<T> clazz) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        objectMapper.setDateFormat(df);
        try(var inputStream = Files.newInputStream(Path.of(jsonFilePath))){
            return objectMapper.readValue(inputStream, clazz);
        }

    }
}
