package com.midziklabs.notification.broker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midziklabs.notification.broker.model.UserModelReplica;


public interface UserModelReplicaRepository extends JpaRepository<UserModelReplica, Long> {

}
