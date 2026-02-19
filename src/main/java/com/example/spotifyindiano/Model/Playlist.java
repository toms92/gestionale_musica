package com.example.spotifyindiano.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Data
@Table(name = "playlist")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Relazione: molte playlist -> un utente
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "id_utente",
            nullable = false,
            foreignKey = @ForeignKey(name = "id_utente")
    )
    private Utente utente;

    @Column(name = "nome", nullable = false)
    private String nome;
    private Time durata;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BranoPlaylist> braniPlaylist;

    @Column(name = "data_creazione", nullable = false)
    private Date data_creazione;
}
