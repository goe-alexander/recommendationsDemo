package com.example.recommendations.controller;


import com.example.recommendations.model.CryptoDetails;
import com.example.recommendations.model.PeriodOfTimeForCrypto;
import com.example.recommendations.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Validated
@RestController
@RequestMapping(value = "/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @Operation(summary = "Gets the oldest value historically for a crypto",
            description = "This endpoint accepts a crypto type and a start and end Date Time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!/Invalid start/end" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/oldest")
    public ResponseEntity<CryptoDetails> getOldestValueFor(@Valid PeriodOfTimeForCrypto periodOfTimeForCrypto) throws IOException {
        return ResponseEntity.ok(historyService.getOldestCryptoValueForPeriod(periodOfTimeForCrypto));
    }


    @Operation(summary = "Gets the newest value historically for a crypto",
            description = "This endpoint accepts a crypto type and a start and end Date Time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!/Invalid start/end" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/newest")
    public ResponseEntity getNewestValueFor(@Valid PeriodOfTimeForCrypto periodOfTimeForCrypto) throws IOException {
        return ResponseEntity.ok(historyService.getNewestCryptoValueForPeriod(periodOfTimeForCrypto));
    }


    @Operation(summary = "Gets the minimum value historically for a crypto",
            description = "This endpoint accepts a crypto type and a start and end Date Time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!/Invalid start/end" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/min")
    public ResponseEntity<CryptoDetails> getMinValueFor(@Valid PeriodOfTimeForCrypto periodOfTimeForCrypto) throws IOException {
        return ResponseEntity.ok(historyService.getMinCryptoValueForPeriod(periodOfTimeForCrypto));
    }


    @Operation(summary = "Gets the maximum value historically for a crypto",
            description = "This endpoint accepts a crypto type and a start and end Date Time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid Crypto type!/Invalid start/end" ),
            @ApiResponse(responseCode = "404", description = "Data not found")})
    @GetMapping(value = "/max")
    public ResponseEntity<CryptoDetails> getMaxValueFor(@Valid PeriodOfTimeForCrypto periodOfTimeForCrypto) throws IOException {
        return ResponseEntity.ok(historyService.getMaxCryptoValueForPeriod(periodOfTimeForCrypto));
    }

}
