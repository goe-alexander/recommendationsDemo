package com.example.recommendations.service;

import com.example.recommendations.exceptions.ContentNotFoundException;
import com.example.recommendations.model.CryptoDetails;
import com.example.recommendations.model.CryptoType;
import com.example.recommendations.model.NormalizedRange;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class RecommendationService {
    private final CsvReaderService csvReaderService;

    public RecommendationService(CsvReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    // Endpoint exposed services
    public CryptoDetails getOldestCryptoValueFor(CryptoType cryptoType) throws IOException {
        return csvReaderService.readCryptoValuesFor(cryptoType)
                .stream()
                .sorted(Comparator.comparing(CryptoDetails::getTimestamp))
                .findFirst()
                .orElseThrow(() -> new ContentNotFoundException());
    }

    public CryptoDetails getNewestCryptoValueFor(CryptoType cryptoType) throws IOException {
        return csvReaderService.readCryptoValuesFor(cryptoType)
                .stream()
                .sorted(Comparator.comparing(CryptoDetails::getTimestamp).reversed())
                .findFirst()
                .orElseThrow(() -> new ContentNotFoundException());
    }

    public CryptoDetails getMaxCryptoValueFor(CryptoType cryptoType) throws IOException {
        return csvReaderService.readCryptoValuesFor(cryptoType)
                .stream()
                .sorted(Comparator.comparing(CryptoDetails::getPrice).reversed())
                .findFirst()
                .orElseThrow(() -> new ContentNotFoundException());
    }

    public CryptoDetails getMinCryptoValueFor(CryptoType cryptoType) throws IOException {
        return csvReaderService.readCryptoValuesFor(cryptoType)
                .stream()
                .sorted(Comparator.comparing(CryptoDetails::getPrice))
                .findFirst()
                .orElseThrow(() -> new ContentNotFoundException());
    }

    public List<NormalizedRange> getNormalizedRangeListDesc() throws IOException {
        return csvReaderService.readAllCryptoValues()
                .stream()
                .map(this::calculateNormalizedRange)
                .sorted(Comparator.comparing(NormalizedRange::getNormalizedRange))
                .collect(Collectors.toList());
    }

    public NormalizedRange getHighestNormalizedRangeForDay(LocalDate day) throws IOException {
        return csvReaderService.readAllCryptoValues()
                .stream()
                .map(cryptoList -> cryptoList.stream()
                        .filter(isInDay(day))
                        .collect(Collectors.toList()))
                .map(this::calculateNormalizedRange)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(NormalizedRange::getNormalizedRange))
                .orElseThrow(() -> new ContentNotFoundException());
    }


    // Easy use of summary statistics.
    // This could be easily optimized with custom collector that only get's max and min, and none of the other metrics
    private NormalizedRange calculateNormalizedRange(List<CryptoDetails> cryptoValues) {
        if(cryptoValues.size() == 0) {
            return null;
        }
        DoubleSummaryStatistics cryptoStats = cryptoValues.stream()
                .map(CryptoDetails::getPrice)
                .collect(Collectors.summarizingDouble(Double::doubleValue));

        Double normalizedRange = (cryptoStats.getMax() - cryptoStats.getMin()) / cryptoStats.getMin();
        return NormalizedRange
                .builder()
                .symbol(cryptoValues.get(0).getSymbol())
                .normalizedRange(normalizedRange)
                .build();
    }

    static Predicate<CryptoDetails> isInDay(LocalDate day) {
        return cryptoDetails ->
                cryptoDetails.getTimestamp().toLocalDate().compareTo(day) == 0;
    }

}
