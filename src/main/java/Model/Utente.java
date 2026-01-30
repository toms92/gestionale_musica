package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue
    private long id;

    private boolean admin;
    private String nome_utente;
    private String password;
    private Date data_registrazione;

}
