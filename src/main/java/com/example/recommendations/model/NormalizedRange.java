package com.example.recommendations.model;

import com.example.recommendations.model.CryptoType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NormalizedRange {
    private CryptoType symbol;
    private Double normalizedRange;
}
