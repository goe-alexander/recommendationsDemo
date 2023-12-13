package com.example.recommendations.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum CryptoType {
    BTC, DOGE, ETH, LTC, XRP;

    public static final String getListOfAvailableEnums() {
        return Arrays.stream(CryptoType.values())
                .map(cryptoType -> cryptoType.name())
                .collect(Collectors.joining(","));
    }
}
