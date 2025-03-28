package com.midziklabs.notification.broker.consumer;

import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midziklabs.notification.model.MailNotificationModel;
import com.midziklabs.notification.repository.MailNotificationRepository;
import com.midziklabs.notification.service.MailNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class RejectionMessageConsumer {

    private final MailNotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "rejection-event", groupId = "notification-group")
    public void listen(String message) {
        log.info("Message received: {}",message);
        try {
            Map<String, String> message_data = objectMapper.readValue(message, Map.class);
            log.info("Message data: {}", message_data);
            String to = message_data.get("user_email");
            log.info("Email recipient: ", to);
            String subject = "Changes Required to Your Advertisement Notification";
            String body = "Please check your notifications on the portal to see the changes required to your advertisement";
            notificationService.sendMail(to, subject, body);
            notificationService.saveRejectionNotification(message_data);
        } catch (JsonProcessingException e) {
            log.error("Error parsing message", e);
        }
        catch (Exception e) {
            log.error("Error processing message", e);
        }
        
    }
}
