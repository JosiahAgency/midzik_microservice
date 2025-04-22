package com.midziklabs.payments.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midziklabs.payments.PaymentsApplication;
import com.midziklabs.payments.feignclient.PesapalClient;
import com.midziklabs.payments.requestDto.AuthenticationRequestDto;
import com.midziklabs.payments.requestDto.OrderRequestDto;
import com.midziklabs.payments.responseDto.AuthenticationResponseDto;
import com.midziklabs.payments.responseDto.OrderErrorDto;
import com.midziklabs.payments.responseDto.OrderResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    @Value("${pesapal.consumer_key}")
    private String consumer_key;
    @Value("${pesapal.consumer_secret}")
    private String consumer_secret;

    private final PesapalClient pesapalClient;
    private final ObjectMapper objectMapper;

    @Cacheable(value = "token", key = "'accessToken'", unless = "#result == null || #result.token == null")
    private AuthenticationResponseDto authenticate() {
        AuthenticationRequestDto req = new AuthenticationRequestDto();
        req.setConsumer_key(consumer_key);
        req.setConsumer_secret(consumer_secret);
        ResponseEntity<AuthenticationResponseDto> response = pesapalClient.getAccessToken(req);
        AuthenticationResponseDto authResponse = response.getBody();
        log.info("Authentication Response: {}", authResponse);
        return authResponse;
    }

    public Object submitOrderRequest(OrderRequestDto request) {
        AuthenticationResponseDto authResponse = authenticate();
        // if (authResponse.getStatus() !== "200") {
        // throw new RuntimeException("Authentication failed: " +
        // authResponse.getMessage());
        // }
        String token = authResponse.getToken();
        ResponseEntity<?> response = pesapalClient.submitOrderRequest(request, "Bearer " + token);
        log.info("Response: {}", response);
        if (response.getBody() instanceof Map) {
            Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
            if (!responseBody.get("status").equals("200")) {
                OrderErrorDto errorDto = objectMapper.convertValue(responseBody, OrderErrorDto.class);
                throw new RuntimeException("Order request failed: " + errorDto.getError().getMessage());
            }
        }
        log.info("Response from Pesapal: {}", response);
        return response.getBody();
    }

}
