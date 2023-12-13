package com.example.recommendations.service;

import com.example.recommendations.model.CryptoDetails;
import com.example.recommendations.model.CryptoType;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CsvReaderService {

    @Value("${crypto.prices.location}")
    private String PRICES_LOCATION;

    private final ResourceLoader resourceLoader;
    private final ObjectReader csvReader;

    private CsvMapper mapper = new CsvMapper();
    private CsvSchema schema = CsvSchema.builder()
                                        .addColumn("timestamp")
                                        .addColumn("symbol")
                                        .addColumn("price")
                                        .build().withHeader();
    public CsvReaderService(ResourceLoader resourceLoader, ObjectReader csvReader) {
        this.resourceLoader = resourceLoader;
        this.csvReader = csvReader;
    }

    public List<CryptoDetails> readCryptoValuesFor(CryptoType cryptoType) throws IOException {
        String fileName = cryptoType.name() + "_values.csv";
        Resource resource = resourceLoader.getResource("classpath:" + PRICES_LOCATION + "/" + fileName);
        File inputFile =  resource.getFile();

        return readCryptoDetailsList(inputFile);
    }

    public List<List<CryptoDetails>> readAllCryptoValues() throws IOException {
        File cryptoFolder = ResourceUtils.getFile("classpath:" + PRICES_LOCATION + "/");
        List<List<CryptoDetails>> result = new ArrayList<>();
        for (File inputFile : cryptoFolder.listFiles() ) {
            result.add(readCryptoDetailsList(inputFile));
        }

        return result;
    }


    private List<CryptoDetails> readCryptoDetailsList(File inputFile) throws IOException {
        MappingIterator<CryptoDetails> iterator =  mapper.readerFor(CryptoDetails.class).with(schema).readValues(inputFile);
        return iterator.readAll();
    }
}
