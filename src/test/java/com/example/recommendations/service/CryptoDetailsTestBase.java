package com.example.recommendations.service;

import com.example.recommendations.model.CryptoDetails;
import com.example.recommendations.model.CryptoType;
import com.example.recommendations.model.NormalizedRange;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.recommendations.model.CryptoType.*;
import static com.example.recommendations.model.CryptoType.XRP;

public class CryptoDetailsTestBase {

    public static List<CryptoDetails> btcDetails;
    public static List<CryptoDetails> ethDetails;
    public static List<CryptoDetails> xrpDetails;

    public static List<List<CryptoDetails>> allCryptoDetails;
    public static List<NormalizedRange> expectedNormalizedList;
    public static NormalizedRange expectedNormalizedMaxForToday;

    public static Double MAX_PRICE = 6.0;
    public static Double MIN_PRICE = 0.24;

    public static Double MAX_XRP_PRICE = 0.67;
    public static Double MIN_XRP_PRICE = MIN_PRICE;
    public static Double OLDEST_BTC_PRICE = 1.0;
    public static Double NEWEST_ETH_PRICE = MAX_PRICE;
    public static Double MIN_ETH_PRICE = 2.0;
    public static Double MAX_ETH_PRICE = MAX_PRICE;
    public static Double MIN_ETH_PRICE_ON_SAME_DAY = MAX_PRICE-1.5;
    public static Double MAX_BTC_PRICE = 3.0;
    public static Double MIN_BTC_PRICE = OLDEST_BTC_PRICE;


    public static LocalDateTime now = LocalDateTime.now();


    //Tests are added quite quickly, so I bunched up together in the same collections of inputs data that would cover multiple scenarios
    //Rather than having each list of inputs always created with just the right amount of data that I need to replicate the scenario.
    static {
        btcDetails = new ArrayList<>();
        btcDetails.add(CryptoDetails.builder().symbol(BTC).timestamp(now.minusDays(5)).price(OLDEST_BTC_PRICE).build());
        btcDetails.add(CryptoDetails.builder().symbol(BTC).timestamp(now.minusDays(1)).price(2.0).build());
        btcDetails.add(CryptoDetails.builder().symbol(BTC).timestamp(now).price(MAX_BTC_PRICE).build());

        ethDetails = new ArrayList<>();
        ethDetails.add(CryptoDetails.builder().symbol(ETH).timestamp(now.minusDays(2)).price(MIN_ETH_PRICE).build());
        ethDetails.add(CryptoDetails.builder().symbol(ETH).timestamp(now.minusDays(1)).price(3.0).build());
        ethDetails.add(CryptoDetails.builder().symbol(ETH).timestamp(now).price(MAX_PRICE).build());
        ethDetails.add(CryptoDetails.builder().symbol(ETH).timestamp(now).price(MIN_ETH_PRICE_ON_SAME_DAY).build());

        xrpDetails = new ArrayList<>();
        xrpDetails.add(CryptoDetails.builder().symbol(XRP).timestamp(now.minusDays(2)).price(0.567).build());
        xrpDetails.add(CryptoDetails.builder().symbol(XRP).timestamp(now.minusDays(1)).price(MAX_XRP_PRICE).build());
        xrpDetails.add(CryptoDetails.builder().symbol(XRP).timestamp(now).price(MIN_PRICE).build());

        allCryptoDetails = new ArrayList<>();
        allCryptoDetails.add(btcDetails);
        allCryptoDetails.add(ethDetails);
        allCryptoDetails.add(xrpDetails);

        expectedNormalizedList = new ArrayList<>();
        NormalizedRange btcNormalizedRange =  NormalizedRange
                .builder()
                .normalizedRange((MAX_BTC_PRICE - MIN_BTC_PRICE)/MIN_BTC_PRICE)
                .symbol(BTC)
                .build();
        NormalizedRange ethNormalizedRange =  NormalizedRange
                .builder()
                .normalizedRange((MAX_ETH_PRICE - MIN_ETH_PRICE)/MIN_ETH_PRICE)
                .symbol(ETH)
                .build();
        NormalizedRange xrtNormalizedRange = NormalizedRange
                .builder()
                .normalizedRange((MAX_XRP_PRICE - MIN_XRP_PRICE)/MIN_XRP_PRICE)
                .symbol(XRP)
                .build();

        expectedNormalizedList.add(xrtNormalizedRange);
        expectedNormalizedList.add(btcNormalizedRange);
        expectedNormalizedList.add(ethNormalizedRange);

        // ONly ETH has multiple values assigned for one day so it will surely be more than 0
        expectedNormalizedMaxForToday = NormalizedRange
                .builder()
                .symbol(ETH)
                .normalizedRange((MAX_PRICE - MIN_ETH_PRICE_ON_SAME_DAY)/MIN_ETH_PRICE_ON_SAME_DAY)
                .build();

    }
}
