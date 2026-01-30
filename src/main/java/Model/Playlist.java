package Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Entity
@Data
@Table(name = "playlist")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Relazione: molte playlist -> un utente
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "id_utente",
            nullable = false,
            foreignKey = @jakarta.persistence.ForeignKey(name = "id_utente")
    )
    private Utente utente;

    private Time durata;
    private Date dataCreazione;
}
