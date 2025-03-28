package com.midziklabs.advertisement.broker.producer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midziklabs.advertisement.requestDto.AdvertisementRejectionRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RejectionMessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendRejectionMessage(AdvertisementRejectionRequest request) throws JsonProcessingException {
        kafkaTemplate.send("rejection-event", objectMapper.writeValueAsString(request));
    }
}
