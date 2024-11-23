package notification.example.demo;

import notification.example.demo.Log.NotificationLogRepository;
/*import notification.example.demo.stats.NotificationLog;
import notification.example.demo.stats.NotificationLogRepository1;*/
import notification.example.demo.SMS.SmsRequest;
import notification.example.demo.SMS.SmsService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotifProcess1 implements ItemProcessor<Notification,Notification> {
    @Autowired
    private MailUtil util;
    @Autowired
    private NotificationLogRepository logRepository;
    private List<String> emailTargets = new ArrayList<>();
    @Override
    public Notification process(Notification notif){
        emailTargets.add(notif.getEmail());
        System.out.println("Mail ciblé : \n" );

        for (String email : emailTargets) {
            System.out.println(notif.getEmail());
        }
       // if ("False".equals(notif.getEst_notifie())) {
        //    System.out.println(notif.getEmail());
            if ("email".equals(notif.getCanal_envoi()) && notif.getEmail().contains("@gmail.com")) {
                // Envoyer un email

                util.sendEmail(notif.getEmail(),build(notif));
                System.out.println("Envoi du mail a : "+notif.getEmail());

                // Mettre à jour le champ est_notifie à true
            }else if ("SMS".equalsIgnoreCase(notif.getCanal_envoi())) {
                // Envoyer un SMS
                String message = buildSmsMessage(notif);
                SmsRequest smsRequest = new SmsRequest(notif.getNum_tel(), message);
                SmsService.sendSms(smsRequest);
                System.out.println("SMS sent to: " + notif.getNum_tel());
            }

            notif.setEst_notifie("True");

       // }
        return notif;
    }

    private String build(Notification notif) {




        String mailBody = "Cher(e) Client(e),\n\n" +
                "Nous vous informons que votre transaction a été effectuée avec succès.\n\n" +
                "Détails de la transaction :\n" +
                "- Référence de la Transaction : " + notif.getId() + "\n" +
                "- Montant : " + notif.getMontant_notif() + " MAD\n" +
                "- Date et Heure : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(notif.getDate_op()) + "\n\n" +
                "Nous vous remercions d'avoir utilisé nos services. Si vous avez des questions ou si vous souhaitez obtenir davantage d'informations, n'hésitez pas à nous contacter.\n\n" +
                "Coordonnées du Service Client : https://www.creditagricole.ma/fr/cam-vous-ecoute\n" +
                "Merci de votre confiance.\n\n" +
                "Cordialement,\n" +
                "L'équipe du Crédit Agricole Maroc";
        return mailBody;
    }


    private String buildSmsMessage(Notification notif) {
        return "Cher(e) Client(e),\n\n" +
                "Nous vous informons que votre transaction a été effectuée avec succès.\n\n" +
                "Détails de la transaction :\n" +
                "- Référence de la Transaction : " + notif.getId() + "\n" +
                "- Montant : " + notif.getMontant_notif() + " MAD\n" +
                "- Date et Heure : " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(notif.getDate_op()) + "\n\n" +
                "Nous vous remercions d'avoir utilisé nos services. Si vous avez des questions ou si vous souhaitez obtenir davantage d'informations, n'hésitez pas à nous contacter.\n\n" +
                "Coordonnées du Service Client : https://www.creditagricole.ma/fr/cam-vous-ecoute\n" +
                "Merci de votre confiance.\n\n" +
                "Cordialement,\n" +
                "L'équipe du Crédit Agricole Maroc";
    }
    /*private void logNotification(Notification notif, String status) {
        NotificationLog log = new NotificationLog();
        log.setNotificationId(Long.valueOf(notif.getId()));
        log.setEmail(notif.getEmail());
        log.setStatus(status.contains("successfully") ? "SUCCESS" : "FAILURE");
        log.setSentDate(new Date());

    }*/

}
