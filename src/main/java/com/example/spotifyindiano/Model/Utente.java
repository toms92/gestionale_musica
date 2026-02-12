package com.example.spotifyindiano.Model;

import com.example.spotifyindiano.Misc.BooleanYnConverter;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Convert(converter = BooleanYnConverter.class)
    @Column(nullable = false)
    private boolean admin = false;


    private String nomeUtente; // Rinominato in camelCase
    private String email;


    @Column(nullable = false)
    private String password;

    private LocalDateTime dataRegistrazione;

    @PrePersist
    protected void onCreate() {
        if (this.dataRegistrazione == null) {
            this.dataRegistrazione = LocalDateTime.now();
        }
    }
}