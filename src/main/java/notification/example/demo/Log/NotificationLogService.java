package notification.example.demo.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationLogService {
    @Autowired
    private NotificationLogRepository notificationLogRepository;

    public void logNotification(String recipient, String message, String notificationType, String status, String errorDetails) {
        Log log = new Log();
        log.setRecipient(recipient);
        log.setMessage(message);
        log.setNotificationType(notificationType);
        log.setTimestamp(LocalDateTime.now());
        log.setStatus(status);
        log.setErrorDetails(errorDetails);
        notificationLogRepository.save(log);
    }
}
