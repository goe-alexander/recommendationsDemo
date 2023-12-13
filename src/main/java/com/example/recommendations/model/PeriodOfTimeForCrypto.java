package com.example.recommendations.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;


@Data
@Builder
@Validated
public class PeriodOfTimeForCrypto {
    @NotNull
    private LocalDateTime start;
    @NotNull
    private LocalDateTime end;
    private CryptoType cryptoType;
}
