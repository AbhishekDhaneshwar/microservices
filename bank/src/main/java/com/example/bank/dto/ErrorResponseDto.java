package com.example.bank.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Schema to hold Error response information")
public class ErrorResponseDto {

    @Schema(description = "API path where the error occurred")
    private String apiPath;

    @Schema(description = "Error code representing the error happened")
    private HttpStatus errorCode;

    @Schema(description = "Error message describing the error")
    private String errorMessage;

    @Schema(description = "Timestamp when the error occurred")
    private LocalDateTime errorTime;

}
