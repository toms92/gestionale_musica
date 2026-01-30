package Model;

import Misc.BooleanYnConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue
    private long id;

    @Convert(converter = BooleanYnConverter.class)
    private boolean admin;

    private String nome_utente;
    private String password;
    private Date data_registrazione;

}
