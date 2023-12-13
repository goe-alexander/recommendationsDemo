package com.example.recommendations.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
public class RequestDetails {
    private AtomicInteger count;
    private LocalDateTime lastUpdate;
}
