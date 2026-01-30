package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "generi")
public class Genere {
    @Id
    @GeneratedValue
    private Long id;

    private String nome;
}
