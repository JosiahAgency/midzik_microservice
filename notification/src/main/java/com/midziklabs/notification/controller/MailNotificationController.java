package com.midziklabs.notification.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.midziklabs.notification.service.MailNotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class MailNotificationController {
    private final MailNotificationService notificationService;

    @GetMapping("/send")
    public ResponseEntity<?> sendMailNotification() {
        notificationService.sendMail("davenyamongo16@gmail.com", "Mail from MidzikLabs", "Yo, whats up?");
        return ResponseEntity.ok().body("Email sent....");
    }
    
    
}
