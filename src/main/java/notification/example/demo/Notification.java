package notification.example.demo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue
    private Integer id;
    private String canal_envoi;
    private Date date_envoi;
    private Date date_notif;
    private Date date_op;
    private String est_envoye;
    private String est_notifie;
    private String file_name;
    private int id_liaison;
    private String message;
    private float montant_notif;
    private int n_client;
    private String num_tel;
    private String email;
    private String type_notif;
    private String valide;
}



