package com.example.recommendations.config;

import com.example.recommendations.model.CryptoDetails;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvConfiguration {

    @Bean
    public ObjectReader csvReader() {
        CsvMapper csvMapper = new CsvMapper();

//        CsvSchema csvSchema = csvMapper.schemaFor(CryptoDetails.class).withHeader();

        return csvMapper.readerWithSchemaFor(CryptoDetails.class);
    }
}
