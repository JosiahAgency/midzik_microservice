package com.midziklabs.payments.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.midziklabs.payments.PaymentsApplication;
import com.midziklabs.payments.dto.AuthenticationRequestDto;
import com.midziklabs.payments.dto.AuthenticationResponseDto;
import com.midziklabs.payments.feignclient.PesapalClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    @Value("${pesapal.consumer_key}")
    private String consumer_key;
    @Value("${pesapal.consumer_secret}")
    private String consumer_secret;

    private final PesapalClient pesapalClient;

    @Cacheable("token")
    private AuthenticationResponseDto authenticate(){
        AuthenticationRequestDto req = new AuthenticationRequestDto();
        req.setConsumer_key(consumer_key);
        req.setConsumer_secret(consumer_secret);
        ResponseEntity<AuthenticationResponseDto> response = pesapalClient.getAccessToken(req);
        return response.getBody();
    }

    



}
