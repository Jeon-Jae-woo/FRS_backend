package com.recommendation.FRS.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage {
    private LocalDateTime localDateTime;
    private String message;
    private String details;
}
