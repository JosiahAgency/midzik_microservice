package com.midziklabs.payments.requestDto;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderRequestDto {
    private String id = UUID.randomUUID().toString();
    private String currency;
    private Float amount;
    private String description = "Paymment to MidzikLabs";
    private String callback_url = "https://4350-41-90-46-64.ngrok-free.app/api/v1/payments/callback-url";
    private String notification_id = "b22b340c-633e-42e1-9d14-dbfcde18d658";
    private BillingAddress billing_address;

    @Data
    public static class BillingAddress{
        private String email_address;
    }
}


