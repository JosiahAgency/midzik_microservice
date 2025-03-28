package com.midziklabs.authentication.broker.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midziklabs.authentication.model.UserModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserModelProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendMessage(UserModel user_model){
        try {
            String user_model_json = objectMapper.writeValueAsString(user_model);
            kafkaTemplate.send("user-events", user_model_json);
            log.info("UserModelProducer: Message sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
