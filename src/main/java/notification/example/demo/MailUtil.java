package notification.example.demo;

import notification.example.demo.Log.NotificationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class MailUtil {
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private NotificationLogService notificationLogService;

    public String sendEmail(String to, String TextBody) {


        String msg = "";
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setText(TextBody);
            message.setSubject("Confirmation de Transaction");
            message.setFrom("anasbaslih@gmail.com");
            message.setTo(to);
            sender.send(message);
            msg = "mail triggered successfully to : " + to;
            //Suivi et journalisation cas envoi
            notificationLogService.logNotification(to, TextBody, "EMAIL", "SENT", null);
        } catch (Exception e) {
            msg = e.getMessage();
            //Suivi et journalisation cas echec
            notificationLogService.logNotification(to, TextBody, "EMAIL", "FAILED", e.getMessage());
        }
        return msg;
    }
}

