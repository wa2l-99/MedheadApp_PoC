package com.poc.notification.dao;

import com.poc.notification.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String> {
}
