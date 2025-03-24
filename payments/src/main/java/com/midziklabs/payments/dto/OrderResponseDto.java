package com.midziklabs.payments.dto;

import lombok.Data;

@Data
public class OrderResponseDto {
    private String order_tracking_id;
    private String merchant_reference;
    private String redirect_url;
    private Integer error;
    private String message;
}
