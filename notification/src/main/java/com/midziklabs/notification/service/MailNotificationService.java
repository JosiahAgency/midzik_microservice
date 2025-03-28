package com.midziklabs.notification.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.midziklabs.notification.broker.model.UserModelReplica;
import com.midziklabs.notification.broker.repository.UserModelReplicaRepository;
import com.midziklabs.notification.model.MailNotificationModel;
import com.midziklabs.notification.repository.MailNotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailNotificationService {
    private final JavaMailSender mailSender;
    private final MailNotificationRepository notificationRepository;
    private final UserModelReplicaRepository userModelReplicaRepository;

    public void sendMail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@midziklabs.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        log.info("Sending email....");
        mailSender.send(message);
        log.info("Email sent....");
    }

    public MailNotificationModel saveRejectionNotification(Map<String, String> message_data){
        Optional<UserModelReplica> reviewer = userModelReplicaRepository.findById(Long.parseLong(message_data.get("reviewer_id")));
        if(reviewer.isEmpty()){
            log.error("Reviewer not found");
            return null;
        }
        MailNotificationModel notification = new MailNotificationModel();
        notification.setReviewer(reviewer.get().getName());
        notification.setUser_email(message_data.get("user_email"));
        notification.setAdId(message_data.get("adId"));
        notification.setMessage(message_data.get("rejectionReason"));
        notification.setStatus("Unread");
        notification.setCreated_at(String.valueOf(System.currentTimeMillis()));
        return notificationRepository.save(notification);
    }

    public List<MailNotificationModel> getNotifications(String email){
        return notificationRepository.findByUserEmail(email);
    }


}
