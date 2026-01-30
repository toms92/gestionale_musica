package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "brani")
public class Brano {
    @Id
    @GeneratedValue
    private Long id;

    private String titolo;
    private Time durata;
    private String link_yt;
    private String link_mp4;

}
