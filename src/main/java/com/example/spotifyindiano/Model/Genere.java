package com.example.spotifyindiano.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "generi")
public class Genere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_genere", nullable = false)
    private String nome;

    @ManyToMany(mappedBy = "generi")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Brano> braniAppartenenti;
}
