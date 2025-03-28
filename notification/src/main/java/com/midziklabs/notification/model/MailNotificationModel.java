package com.midziklabs.notification.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_mail_notification")
@NoArgsConstructor
public class MailNotificationModel {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String reviewer;
    private String user_email;
    private String AdId;
    private String message;
    private String status;
    private String created_at;
}
