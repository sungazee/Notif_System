package notification.example.demo.Log;

import notification.example.demo.Notification;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NotificationStepExecutionListener extends ItemListenerSupport<Notification, Notification> {

    private NotificationLogService notificationLogService;

    public NotificationStepExecutionListener(NotificationLogService notificationLogService) {
        this.notificationLogService = notificationLogService;
    }

    @Override
    public void afterWrite(Chunk<? extends Notification> items) {
        for (Notification notification : items) {
            notificationLogService.logNotification(
                    notification.getEmail(),
                    notification.getMessage(),
                    "EMAIL",
                    "SENT",
                    null
            );
        }
    }


    @Override
    public void onWriteError(Exception exception, Chunk<? extends Notification> items) {
        for (Notification notification : items) {
            notificationLogService.logNotification(
                    notification.getEmail(),
                    notification.getMessage(),
                    "EMAIL",
                    "FAILED",
                    exception.getMessage()
            );
        }
    }
}