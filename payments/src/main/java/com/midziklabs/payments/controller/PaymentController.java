package com.midziklabs.payments.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.midziklabs.payments.requestDto.OrderRequestDto;
import com.midziklabs.payments.responseDto.AuthenticationResponseDto;
import com.midziklabs.payments.responseDto.OrderResponseDto;
import com.midziklabs.payments.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/welcome")
    public ResponseEntity<String> getPayment() {
        return ResponseEntity.ok("Hello from Payment Controller");
    }

    // @GetMapping("/authenticate")
    // public ResponseEntity<?> authenticate(){
    //     // Call the service to authenticate and get the token
    //     // AuthenticationResponseDto token = paymentService.authenticate();
    //     // return ResponseEntity.ok(token);
    //     AuthenticationResponseDto response = paymentService.authenticate();
    //     if(response.getStatus() != "200"){
    //         return ResponseEntity.status(Integer.valueOf(response.getStatus())).body(response);
    //     }
    //     return ResponseEntity.ok().body(response);
    // }

    @PostMapping("/order-request")
    public ResponseEntity<?> submitOrderRequest(@RequestBody OrderRequestDto requestDto){
        try {
            log.info("Order request received: {}", requestDto);
            Object response = paymentService.submitOrderRequest(requestDto);
            log.info("Response: {}", response.toString());
            return ResponseEntity.ok().body(response);
        } catch (RuntimeException e) {
            log.error("Error occurred while submitting order request: {}", e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }
        
    }

    @GetMapping("/ipn")
    public ResponseEntity<String> ipn(
        @RequestParam("OrderTrackingId") String orderTrackingId, 
        @RequestParam("OrderNotificationType") String orderNotificationType,
        @RequestParam("OrderMerchantReference") String orderMerchantReference
    ){
        log.info("IPN received: OrderTrackingId={}, OrderNotificationType={}, OrderMerchantReference={}", 
            orderTrackingId, orderNotificationType, orderMerchantReference
        );
        //TODO: GetTransactionStatus here
        return ResponseEntity.ok("Hello from IPN");
    } 
    @GetMapping("/callback-url")
    public ResponseEntity<String> redirectUrl(
        @RequestParam("OrderTrackingId") String orderTrackingId, 
        // @RequestParam("OrderNotificationType") String orderNotificationType,
        @RequestParam("OrderMerchantReference") String orderMerchantReference
        ){
        log.info("Callback URL called.....");
        return ResponseEntity.ok("Hello from Redirect URL");
    }

}
