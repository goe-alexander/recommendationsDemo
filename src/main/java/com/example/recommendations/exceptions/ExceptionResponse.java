package com.example.recommendations.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
public class ExceptionResponse {
    public String message;
    public LocalDateTime timestmap;

    public ExceptionResponse(String message, LocalDateTime timestmap) {
        this.message = message;
        this.timestmap = timestmap;
    }

    public ExceptionResponse() {
    }
}
