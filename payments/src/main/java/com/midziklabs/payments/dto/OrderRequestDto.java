package com.midziklabs.payments.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String id;
    private String currency;
    private Float amount;
    private String description;
    private String callback_url;
    private UUID notification_id;
    private BillingAddress billing_adddress;
}

final class BillingAddress{
    private String email_address;
}