package com.example.recommendations.service;

import com.example.recommendations.exceptions.ContentNotFoundException;
import com.example.recommendations.model.CryptoDetails;
import com.example.recommendations.model.PeriodOfTimeForCrypto;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.util.Collections;

import static com.example.recommendations.model.CryptoType.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HistoryServiceTests extends CryptoDetailsTestBase{

    CsvReaderService csvReaderService = mock(CsvReaderService.class);
    HistoryService historyService = new HistoryService(csvReaderService);


    //Oldest endpoint tests
    @Test
    void testGetOldestCryptoValueForPeriodIsSuccessful() throws IOException {
        when(csvReaderService.readCryptoValuesFor(BTC)).thenReturn(btcDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(BTC)
                .start(now.minusDays(1))
                .end(now)
                .build();


        CryptoDetails cryptoDetails = historyService.getOldestCryptoValueForPeriod(periodOfTimeForCrypto);
        assertThat(cryptoDetails.getPrice()).isEqualTo(2.0);
    }


    @Test
    void testGetOldestCryptoValueForPeriodLargerThanRecordedValue() throws IOException {
        when(csvReaderService.readCryptoValuesFor(BTC)).thenReturn(btcDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(BTC)
                .start(now.minusDays(10))
                .end(now)
                .build();


        CryptoDetails cryptoDetails = historyService.getOldestCryptoValueForPeriod(periodOfTimeForCrypto);
        assertThat(cryptoDetails.getPrice()).isEqualTo(OLDEST_BTC_PRICE);
    }

    @Test
    void testGetOldestCryptoValueForPeriodReturnsNotFoundWhenEmptyListRead() throws IOException {
        when(csvReaderService.readCryptoValuesFor(BTC)).thenReturn(Collections.emptyList());
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(BTC)
                .start(now.minusDays(10))
                .end(now)
                .build();


        assertThrows(ContentNotFoundException.class, () -> historyService.getOldestCryptoValueForPeriod(periodOfTimeForCrypto));
    }

    @Test
    void testGetOldestCryptoValueForPeriodReturnsNotFoundWhenNoValueInSource() throws IOException {
        when(csvReaderService.readCryptoValuesFor(BTC)).thenReturn(btcDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(BTC)
                .start(now.minusDays(20))
                .end(now.minusDays(10))
                .build();


        assertThrows(ContentNotFoundException.class, () -> historyService.getOldestCryptoValueForPeriod(periodOfTimeForCrypto));
    }


    //Newest endpoint tests
    @Test
    void testGetNewestCryptoValueForPeriodIsSuccessful() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(ethDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(ETH)
                .start(now.minusDays(1))
                .end(now)
                .build();


        CryptoDetails cryptoDetails = historyService.getNewestCryptoValueForPeriod(periodOfTimeForCrypto);
        assertThat(cryptoDetails.getPrice()).isEqualTo(6.0);
    }


    @Test
    void testGetNewestValueForPeriodLargerThanRecordedValue() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(ethDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(ETH)
                .start(now.minusDays(10))
                .end(now)
                .build();


        CryptoDetails cryptoDetails = historyService.getNewestCryptoValueForPeriod(periodOfTimeForCrypto);
        assertThat(cryptoDetails.getPrice()).isEqualTo(6.0);
    }

    @Test
    void testGetNewestCryptoValueForPeriodReturnsNotFoundWhenEmptyListRead() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(Collections.emptyList());
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(ETH)
                .start(now.minusDays(10))
                .end(now)
                .build();


        assertThrows(ContentNotFoundException.class, () -> historyService.getNewestCryptoValueForPeriod(periodOfTimeForCrypto));
    }

    @Test
    void testGetNewestCryptoValueForPeriodReturnsNotFoundWhenNoValueInSource() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(ethDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(ETH)
                .start(now.minusDays(20))
                .end(now.minusDays(10))
                .build();


        assertThrows(ContentNotFoundException.class, () -> historyService.getNewestCryptoValueForPeriod(periodOfTimeForCrypto));
    }

    // Max endpoint tests
    @Test
    void testGetMaxCryptoValueForPeriodIsSuccessful() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(xrpDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(XRP)
                .start(now.minusDays(1))
                .end(now)
                .build();


        CryptoDetails cryptoDetails = historyService.getMaxCryptoValueForPeriod(periodOfTimeForCrypto);
        assertThat(cryptoDetails.getPrice()).isEqualTo(MAX_XRP_PRICE);
    }


    @Test
    void testGetMaxValueForPeriodLargerThanRecordedValue() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(xrpDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(XRP)
                .start(now.minusDays(10))
                .end(now)
                .build();


        CryptoDetails cryptoDetails = historyService.getMaxCryptoValueForPeriod(periodOfTimeForCrypto);
        assertThat(cryptoDetails.getPrice()).isEqualTo(MAX_XRP_PRICE);
    }

    @Test
    void testGetMaxCryptoValueForPeriodReturnsNotFoundWhenEmptyListRead() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(Collections.emptyList());
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(XRP)
                .start(now.minusDays(10))
                .end(now)
                .build();


        assertThrows(ContentNotFoundException.class, () -> historyService.getMaxCryptoValueForPeriod(periodOfTimeForCrypto));
    }

    @Test
    void testGetMaxCryptoValueForPeriodReturnsNotFoundWhenNoValueInSource() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(xrpDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(XRP)
                .start(now.minusDays(20))
                .end(now.minusDays(10))
                .build();


        assertThrows(ContentNotFoundException.class, () -> historyService.getMaxCryptoValueForPeriod(periodOfTimeForCrypto));
    }

    // Min tests
    @Test
    void testGetMinCryptoValueForPeriodIsSuccessful() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(xrpDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(XRP)
                .start(now.minusDays(1))
                .end(now)
                .build();


        CryptoDetails cryptoDetails = historyService.getMinCryptoValueForPeriod(periodOfTimeForCrypto);
        assertThat(cryptoDetails.getPrice()).isEqualTo(MIN_XRP_PRICE);
    }


    @Test
    void testGetMinValueForPeriodLargerThanRecordedValue() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(xrpDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(XRP)
                .start(now.minusDays(10))
                .end(now)
                .build();


        CryptoDetails cryptoDetails = historyService.getMinCryptoValueForPeriod(periodOfTimeForCrypto);
        assertThat(cryptoDetails.getPrice()).isEqualTo(MIN_XRP_PRICE);
    }

    @Test
    void testGetMinCryptoValueForPeriodReturnsNotFoundWhenEmptyListRead() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(Collections.emptyList());
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(XRP)
                .start(now.minusDays(10))
                .end(now)
                .build();


        assertThrows(ContentNotFoundException.class, () -> historyService.getMinCryptoValueForPeriod(periodOfTimeForCrypto));
    }

    @Test
    void testGetMinCryptoValueForPeriodReturnsNotFoundWhenNoValueInSource() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(xrpDetails);
        PeriodOfTimeForCrypto periodOfTimeForCrypto = PeriodOfTimeForCrypto
                .builder()
                .cryptoType(XRP)
                .start(now.minusDays(20))
                .end(now.minusDays(10))
                .build();


        assertThrows(ContentNotFoundException.class, () -> historyService.getMinCryptoValueForPeriod(periodOfTimeForCrypto));
    }
}
