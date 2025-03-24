package com.midziklabs.payments.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationRequestDto {
    private String consumer_key;
    private String consumer_secret;
}
