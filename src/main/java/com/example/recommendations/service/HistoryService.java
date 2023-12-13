package com.example.recommendations.service;


import com.example.recommendations.exceptions.ContentNotFoundException;
import com.example.recommendations.model.CryptoDetails;
import com.example.recommendations.model.PeriodOfTimeForCrypto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.function.Predicate;

@Service
public class HistoryService {

    private final CsvReaderService csvReaderService;

    public HistoryService(CsvReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    public CryptoDetails getOldestCryptoValueForPeriod(PeriodOfTimeForCrypto periodOfTimeForCrypto) throws IOException {
        return csvReaderService.readCryptoValuesFor(periodOfTimeForCrypto.getCryptoType())
                .stream()
                .filter(isWithinPeriodOfTime(periodOfTimeForCrypto))
                .sorted(Comparator.comparing(CryptoDetails::getTimestamp))
                .findFirst()
                .orElseThrow(() -> new ContentNotFoundException());

    }

    public CryptoDetails getNewestCryptoValueForPeriod(PeriodOfTimeForCrypto periodOfTimeForCrypto) throws IOException {
        return csvReaderService.readCryptoValuesFor(periodOfTimeForCrypto.getCryptoType())
                .stream()
                .filter(isWithinPeriodOfTime(periodOfTimeForCrypto))
                .sorted(Comparator.comparing(CryptoDetails::getTimestamp).reversed())
                .findFirst()
                .orElseThrow(() -> new ContentNotFoundException());
    }

    public CryptoDetails getMaxCryptoValueForPeriod(PeriodOfTimeForCrypto periodOfTimeForCrypto) throws IOException {
        return csvReaderService.readCryptoValuesFor(periodOfTimeForCrypto.getCryptoType())
                .stream()
                .filter(isWithinPeriodOfTime(periodOfTimeForCrypto))
                .sorted(Comparator.comparing(CryptoDetails::getPrice).reversed())
                .findFirst()
                .orElseThrow(() -> new ContentNotFoundException());
    }

    public CryptoDetails getMinCryptoValueForPeriod(PeriodOfTimeForCrypto periodOfTimeForCrypto) throws IOException {
        return csvReaderService.readCryptoValuesFor(periodOfTimeForCrypto.getCryptoType())
                .stream()
                .filter(isWithinPeriodOfTime(periodOfTimeForCrypto))
                .sorted(Comparator.comparing(CryptoDetails::getPrice))
                .findFirst()
                .orElseThrow(() -> new ContentNotFoundException());
    }


    static Predicate<CryptoDetails> isWithinPeriodOfTime(PeriodOfTimeForCrypto periodOfTimeForCrypto) {
        return cryptoDetails ->
                cryptoDetails.getTimestamp().compareTo(periodOfTimeForCrypto.getEnd()) <= 0 &&
                        cryptoDetails.getTimestamp().compareTo(periodOfTimeForCrypto.getStart()) >= 0;
    }

}
