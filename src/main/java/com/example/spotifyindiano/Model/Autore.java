package com.example.spotifyindiano.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "autori")
public class Autore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Column(name = "ascoltatori_mensili", nullable = false)
    private String ascoltatoriMensili;

}
