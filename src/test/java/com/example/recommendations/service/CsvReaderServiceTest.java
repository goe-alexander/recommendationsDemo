package com.example.recommendations.service;

import com.example.recommendations.model.CryptoDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static com.example.recommendations.model.CryptoType.BTC;
import static com.example.recommendations.model.CryptoType.ETH;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


//This still needs a bit of rework to use the prices folder inside the test package
//Currently it uses the src one.
@SpringBootTest
@ActiveProfiles("test")
public class CsvReaderServiceTest {

    @Autowired
    CsvReaderService csvReaderService;

    @Test
    public void testReadCryptoValueForCryptoThatDoesNotExistThrowsError() throws IOException {
        assertThrows(FileNotFoundException.class, () -> csvReaderService.readCryptoValuesFor(ETH));
    }

    @Test
    public void testReadCryptoValueForCryptoTypeWorks() throws IOException {
        List<CryptoDetails> cryptoDetails = csvReaderService.readCryptoValuesFor(BTC);
        assertThat(cryptoDetails).isNotNull();
    }

    @Test
    public void testReadAllCryptoValuesWorks() throws IOException {
        List<List<CryptoDetails>> allCryptoDetails = csvReaderService.readAllCryptoValues();
        assertThat(allCryptoDetails.size()).isEqualTo(2);
    }
}
