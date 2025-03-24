package com.midziklabs.payments.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.midziklabs.payments.dto.AuthenticationRequestDto;
import com.midziklabs.payments.dto.AuthenticationResponseDto;

@FeignClient(name = "PesaPalAPI", url = "https://cybqa.pesapal.com")
public interface PesapalClient {

    @PostMapping(value = "/pesapalv3/api/Auth/RequestToken", consumes = "application/json", produces = "application/json" )
    public ResponseEntity<AuthenticationResponseDto> getAccessToken(
        @RequestBody AuthenticationRequestDto auth_request
    );
}
