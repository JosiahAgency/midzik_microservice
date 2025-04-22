package com.midziklabs.payments.responseDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderResponseDto {
    @JsonProperty("order_tracking_id")
    private String order_tracking_id;
    @JsonProperty("merchant_reference")
    private String merchant_reference;
    @JsonProperty("redirect_url")
    private String redirect_url;
    @JsonProperty("error")
    private Integer error;
    @JsonProperty("message")
    private String message;
}
