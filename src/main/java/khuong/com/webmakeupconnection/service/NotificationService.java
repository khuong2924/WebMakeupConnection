package khuong.com.webmakeupconnection.service;


import org.springframework.beans.factory.annotation.Autowired;
import khuong.com.webmakeupconnection.entity.Notification;
import khuong.com.webmakeupconnection.repository.NotificationRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Lấy thông báo theo ID
    public Notification getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        return notification.orElse(null);
    }
}

