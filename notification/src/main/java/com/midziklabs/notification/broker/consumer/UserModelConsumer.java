package com.midziklabs.notification.broker.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.midziklabs.notification.broker.model.UserModelReplica;
import com.midziklabs.notification.broker.repository.UserModelReplicaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserModelConsumer {

    private final ObjectMapper objectMapper;
    private final UserModelReplicaRepository userModelReplicaRepository;

    @KafkaListener(topics = "user-events", groupId = "my-group-id")
    @Transactional
    public void consume(String message) {
        log.info("Message received: {}", message);
        try {
            // Deserialize the message into a Map to extract the role object
            Map<String, Object> messageMap = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {
            });
            Map<String, Object> roleMap = (Map<String, Object>) messageMap.get("role");

            UserModelReplica user = new UserModelReplica();
            user.setName(messageMap.get("name").toString());
            user.setEmail(messageMap.get("email").toString());
            user.setPassword(messageMap.get("password").toString());
            user.setRole_id(Long.valueOf(roleMap.get("id").toString()));
            log.info("USer to be saved: {}", user);
            UserModelReplica new_replica = userModelReplicaRepository.save(user); // Insert if not exists
            log.info("User saved: {}", new_replica.getId());
        } catch (JsonProcessingException e) {
            log.error("Error parsing message", e);
        } catch (DataIntegrityViolationException e) {
            log.error("Error saving user", e);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.error("Error saving user", e);
        }
    }
}