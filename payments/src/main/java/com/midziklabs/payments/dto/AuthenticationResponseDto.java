package com.midziklabs.payments.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationResponseDto {
    private String token;
    private String expiryDate;
    private Object error;
    private String status;
    private String message;
}
