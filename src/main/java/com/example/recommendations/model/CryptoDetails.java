package com.example.recommendations.model;

import com.example.recommendations.utils.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CryptoDetails {
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;
    private CryptoType symbol;
    private Double price;

    public CryptoDetails(LocalDateTime timestamp, CryptoType symbol, Double price) {
        this.timestamp = timestamp;
        this.symbol = symbol;
        this.price = price;
    }

    public CryptoDetails() {
    }
}
