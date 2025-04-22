package com.midziklabs.payments.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.midziklabs.payments.requestDto.AuthenticationRequestDto;
import com.midziklabs.payments.requestDto.OrderRequestDto;
import com.midziklabs.payments.responseDto.AuthenticationResponseDto;
import com.midziklabs.payments.responseDto.OrderResponseDto;

@FeignClient(name = "PesaPalAPI", url = "https://pay.pesapal.com/v3")
public interface PesapalClient {

    @PostMapping(value = "/api/Auth/RequestToken", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<AuthenticationResponseDto> getAccessToken(
        @RequestBody AuthenticationRequestDto auth_request
    );
    @PostMapping(value = "/api/Transactions/SubmitOrderRequest", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> submitOrderRequest(
        @RequestBody OrderRequestDto request,
        @RequestHeader("Authorization") String token
    );
}
