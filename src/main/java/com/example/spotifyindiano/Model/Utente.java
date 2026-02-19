package com.example.spotifyindiano.Model;

import com.example.spotifyindiano.Misc.BooleanYnConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Convert(converter = BooleanYnConverter.class)
    @Column(nullable = false)
    private boolean admin = false;

    @Column(name = "nome_utente", nullable = false)
    private String nomeUtente; // Rinominato in camelCase

    @Column(nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime dataRegistrazione;

    @PrePersist
    protected void onCreate() {
        if (this.dataRegistrazione == null) {
            this.dataRegistrazione = LocalDateTime.now();
        }
    }

}