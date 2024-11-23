package notification.example.demo;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NotificationProcessor implements ItemProcessor<Notification,Notification> {
    @Autowired
    private MailUtil util;
    private List<String> emailTargets = new ArrayList<>();
    @Override
    public Notification process(Notification notif) {
        emailTargets.add(notif.getEmail());
        System.out.println("Mail ciblé : \n" );
        for (String email : emailTargets) {
            System.out.println(notif.getEmail());
        }
        String msg = util.sendEmail(notif.getEmail(),buildMessage(notif));
        System.out.println( "Envoie du mail a : " + notif.getEmail());
        System.out.println(msg);
        return notif;
    }

    private String buildMessage(Notification notif) {
        String mailBody = "Cher(e) Client(e),\n\n"
                + "Nous avons le plaisir de vous annoncer le lancement d'un tout nouveau service au Crédit Agricole Maroc !\n\n"
                + "🌍 **Envoyez de l'argent à l'étranger avec des frais réduits :** Nous sommes ravis de vous présenter notre nouveau service de transfert international d'argent. Ce service vous permet d'envoyer de la monnaie à l'étranger facilement et rapidement, tout en bénéficiant de frais parmi les plus bas du marché.\n\n"
                + "Que vous souhaitiez soutenir un proche ou effectuer un paiement international, notre service vous garantit des tarifs compétitifs et une expérience simplifiée.\n\n"
                + "Pour en savoir plus sur ce nouveau service et découvrir les modalités, rendez-vous dans l'une de nos agences ou contactez notre service client.\n\n"
                + "Merci de votre confiance et pour choisir le Crédit Agricole Maroc pour vos besoins financiers.\n\n"
                + "Cordialement,\n"
                + "L'équipe du Crédit Agricole Maroc";
        return mailBody;
    }


}


