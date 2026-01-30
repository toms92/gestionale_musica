package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "autori")
public class Autore {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;
    private String descrizione;
    private String ascoltatoriMensili;

}
