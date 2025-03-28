package com.midziklabs.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.midziklabs.notification.model.MailNotificationModel;

public interface MailNotificationRepository extends JpaRepository<MailNotificationModel, Long> {
    @Query("SELECT n FROM MailNotificationModel n WHERE n.user_email = :email")
    List<MailNotificationModel> findByUserEmail(@Param("email") String email);
}
