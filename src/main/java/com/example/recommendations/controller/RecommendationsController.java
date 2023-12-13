package com.example.recommendations.controller;

import com.example.recommendations.model.CryptoDetails;
import com.example.recommendations.model.CryptoType;
import com.example.recommendations.model.NormalizedRange;
import com.example.recommendations.service.CsvReaderService;
import com.example.recommendations.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/crypto")
public class RecommendationsController {
    private final CsvReaderService csvReaderService;
    private final RecommendationService recommendationService;

    public RecommendationsController(CsvReaderService csvReaderService, RecommendationService recommendationService) {
        this.csvReaderService = csvReaderService;
        this.recommendationService = recommendationService;
    }


    @Operation(summary = "Gets all the values for the specified crypto",
            description = "This endpoint accepts a list of crypto values that for the moment are: BTC, DOGE, ETH, LTC, XRP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/values")
    public ResponseEntity<List<CryptoDetails>> readValuesFor(@RequestParam @Valid CryptoType cryptoType) throws IOException {
        return ResponseEntity.ok(csvReaderService.readCryptoValuesFor(cryptoType));
    }


    @Operation(summary = "Gets all the values for all available crypto")
    @ApiResponses(value = {@ApiResponse(responseCode = "404", description = "Customer not found")})
    @GetMapping(value = "/values/all")
    public ResponseEntity<List<List<CryptoDetails>>> readAllValues() throws IOException {
        return ResponseEntity.ok(csvReaderService.readAllCryptoValues());
    }


    @Operation(summary = "Gets the oldest value historically for a type of crypto",
            description = "This endpoint accepts a list of crypto values that for the moment are: BTC, DOGE, ETH, LTC, XRP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/oldest")
    public ResponseEntity<CryptoDetails> getOldestValueFor(@RequestParam @Valid CryptoType cryptoType) throws IOException {
        return ResponseEntity.ok(recommendationService.getOldestCryptoValueFor(cryptoType));
    }


    @Operation(summary = "Gets the newest value historically for a type of crypto",
            description = "This endpoint accepts a list of crypto values that for the moment are: BTC, DOGE, ETH, LTC, XRP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/newest")
    public ResponseEntity<CryptoDetails> getNewestValueFor(@RequestParam @Valid CryptoType cryptoType) throws IOException {
        return ResponseEntity.ok(recommendationService.getNewestCryptoValueFor(cryptoType));
    }


    @Operation(summary = "Gets the max value historically for a type of crypto",
            description = "This endpoint accepts a list of crypto values that for the moment are: BTC, DOGE, ETH, LTC, XRP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/max")
    public ResponseEntity<CryptoDetails> getMaxValueFor(@RequestParam @Valid CryptoType cryptoType) throws IOException {
        return ResponseEntity.ok(recommendationService.getMaxCryptoValueFor(cryptoType));
    }

    @Operation(summary = "Gets the min value historically for a type of crypto",
            description = "This endpoint accepts a list of crypto values that for the moment are: BTC, DOGE, ETH, LTC, XRP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/min")
    public ResponseEntity<CryptoDetails> getMinValueFor(@RequestParam @Valid CryptoType cryptoType) throws IOException {
        return ResponseEntity.ok(recommendationService.getMinCryptoValueFor(cryptoType));
    }


    @Operation(summary = "Gets a list of all the normalized ranges for all cryptos",
            description = "Returns a list of complex objects with the crypto type and the normalized range. Normalized range is: (max-min)/min")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/normalized-range")
    public ResponseEntity<List<NormalizedRange>> getNormalizedRangeDesc() throws IOException {
        return ResponseEntity.ok(recommendationService.getNormalizedRangeListDesc());
    }


    @Operation(summary = "Gets maximum normalized range crypto and it's value, for a specific day",
            description = "Returns a list of complex objects with the crypto type and the normalized range. Normalized range is: (max-min)/min")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Date format" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/normalized-range/max")
    public ResponseEntity getNormalizedRangeDesc(@RequestParam @Valid LocalDate day) throws IOException {
        return ResponseEntity.ok(recommendationService.getHighestNormalizedRangeForDay(day));
    }
}

