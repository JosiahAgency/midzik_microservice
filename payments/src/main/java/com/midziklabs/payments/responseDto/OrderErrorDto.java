package com.midziklabs.payments.responseDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderErrorDto {

    @JsonProperty("error")
    private ErrorDetails error;

    @JsonProperty("status")
    private String status;

    @Data
    public static class ErrorDetails {
        @JsonProperty("error_type")
        private String errorType;

        @JsonProperty("code")
        private String code;

        @JsonProperty("message")
        private String message;
    }
}
