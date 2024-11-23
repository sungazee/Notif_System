package notification.example.demo;


/*import notification.example.demo.stats.NotificationLogRepository1;
import notification.example.demo.stats.NotificationLog;*/
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class prtest implements ItemProcessor<Notification, Notification> {

    @Autowired
    private MailUtil util;

    //@Autowired
   // private NotificationLogRepository1 logRepository;

    @Override
    public Notification process(Notification notif) {
        String msg = util.sendEmail(notif.getEmail(), buildMessage(notif));
        //logNotification(notif, msg);
        notif.setEst_notifie("True");
        return notif;
    }

    private String buildMessage(Notification notif) {
        return "Dear " + notif.getId() + ",\n" +
                "Statement of your credit card ending with " + notif.hashCode() + "** has been generated.\n" +
                "Dues amount: " + notif.getMontant_notif() + "\n" +
                "Last payment date: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a").format(notif.getDate_op()) + "\n\n" +
                "If you already paid, please ignore this email.\n" +
                "Thanks for using our credit card service.";
    }

    /*private void logNotification(Notification notif, String status) {
        NotificationLog log = new NotificationLog();
        log.setNotificationId(Long.valueOf(notif.getId()));
        log.setEmail(notif.getEmail());
        log.setStatus(status.contains("successfully") ? "SUCCESS" : "FAILURE");
        log.setSentDate(new Date());
        logRepository.save(log);
    }*/
}