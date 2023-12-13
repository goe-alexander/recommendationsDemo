package com.example.recommendations.service;

import com.example.recommendations.exceptions.ContentNotFoundException;
import com.example.recommendations.model.CryptoDetails;
import com.example.recommendations.model.NormalizedRange;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.example.recommendations.model.CryptoType.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecommendationServiceTests extends CryptoDetailsTestBase{
    CsvReaderService csvReaderService = mock(CsvReaderService.class);
    RecommendationService recommendationService = new RecommendationService(csvReaderService);


    //Oldest service tests
    @Test
    void testGetOldestCryptoValueIsSuccessful() throws IOException {
        when(csvReaderService.readCryptoValuesFor(BTC)).thenReturn(btcDetails);

        CryptoDetails cryptoDetails = recommendationService.getOldestCryptoValueFor(BTC);
        assertThat(cryptoDetails.getPrice()).isEqualTo(OLDEST_BTC_PRICE);
    }

    @Test
    void testGetOldestCryptoValueReturnsNotFoundWhenEmptyListRead() throws IOException {
        when(csvReaderService.readCryptoValuesFor(BTC)).thenReturn(emptyList());

        assertThrows(ContentNotFoundException.class, () -> recommendationService.getOldestCryptoValueFor(BTC));
    }

    //Newest service tests
    @Test
    void testGetNewestCryptoValueIsSuccessful() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(ethDetails);

        CryptoDetails cryptoDetails = recommendationService.getNewestCryptoValueFor(ETH);
        assertThat(cryptoDetails.getPrice()).isEqualTo(NEWEST_ETH_PRICE);
    }

    @Test
    void testGetNewestCryptoValueReturnsNotFoundWhenEmptyListRead() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(emptyList());

        assertThrows(ContentNotFoundException.class, () -> recommendationService.getOldestCryptoValueFor(ETH));
    }

    //Max service tests
    @Test
    void testGetMaxCryptoForEth() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(ethDetails);

        CryptoDetails cryptoDetails = recommendationService.getMaxCryptoValueFor(ETH);
        assertThat(cryptoDetails.getPrice()).isEqualTo(MAX_ETH_PRICE);
    }

    @Test
    void testGetMaxCryptoForXrp() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(xrpDetails);

        CryptoDetails cryptoDetails = recommendationService.getMaxCryptoValueFor(XRP);
        assertThat(cryptoDetails.getPrice()).isEqualTo(MAX_XRP_PRICE);
    }

    @Test
    void testGetMaxCryptoValueReturnsNotFoundWhenEmptyListRead() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(emptyList());

        assertThrows(ContentNotFoundException.class, () -> recommendationService.getMaxCryptoValueFor(ETH));
    }


    //Min service tests
    @Test
    void testGetMinCryptoForEth() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(ethDetails);

        CryptoDetails cryptoDetails = recommendationService.getMinCryptoValueFor(ETH);
        assertThat(cryptoDetails.getPrice()).isEqualTo(MIN_ETH_PRICE);
    }

    @Test
    void testGetMinCryptoForXrp() throws IOException {
        when(csvReaderService.readCryptoValuesFor(XRP)).thenReturn(xrpDetails);

        CryptoDetails cryptoDetails = recommendationService.getMinCryptoValueFor(XRP);
        assertThat(cryptoDetails.getPrice()).isEqualTo(MIN_XRP_PRICE);
    }

    @Test
    void testGetMinCryptoValueReturnsNotFoundWhenEmptyListRead() throws IOException {
        when(csvReaderService.readCryptoValuesFor(ETH)).thenReturn(emptyList());

        assertThrows(ContentNotFoundException.class, () -> recommendationService.getMinCryptoValueFor(ETH));
    }

    // Normalized List Descending
    @Test
    void testGetNormalizedRangeListDesc() throws IOException {
        when(csvReaderService.readAllCryptoValues()).thenReturn(allCryptoDetails);

        List<NormalizedRange> normalizedRangeListDesc = recommendationService.getNormalizedRangeListDesc();
        assertThat(normalizedRangeListDesc).isEqualTo(expectedNormalizedList);
    }

    @Test
    void testGetNormalizedRangeListDescReturnsEmptyListWhenEmptyListRead() throws IOException {
        when(csvReaderService.readAllCryptoValues()).thenReturn(emptyList());

        List<NormalizedRange> normalizedRangeListDesc = recommendationService.getNormalizedRangeListDesc();
        assertThat(normalizedRangeListDesc).isEqualTo(emptyList());
    }

    @Test
    void testGetNormalizedRangeForDayWhenOnlyOneValueExists() throws IOException {
        when(csvReaderService.readAllCryptoValues()).thenReturn(allCryptoDetails);

        NormalizedRange normalizedRange = recommendationService.getHighestNormalizedRangeForDay(now.minusDays(1).toLocalDate());
        assertThat(normalizedRange.getNormalizedRange()).isEqualTo(0.0);
    }

    @Test
    void testGetNormalizedRangeForDay() throws IOException {
        when(csvReaderService.readAllCryptoValues()).thenReturn(allCryptoDetails);

        NormalizedRange normalizedRangeMax = recommendationService.getHighestNormalizedRangeForDay(now.toLocalDate());
        assertThat(normalizedRangeMax).isEqualTo(expectedNormalizedMaxForToday);
    }
}
