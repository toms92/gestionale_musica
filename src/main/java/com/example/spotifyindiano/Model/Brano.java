package com.example.spotifyindiano.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.sql.Time;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "brani")
public class Brano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "titolo", nullable = false)
    private String titolo;

    @Column(name = "durata", nullable = false)
    private Time durata;

    @ManyToMany
    @JoinTable(
            name = "generi_brani",
            joinColumns = @JoinColumn(name = "id_brano", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_genere", referencedColumnName = "id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Genere> generi;

    @Column(name = "link_yt", nullable = false)
    private String link_yt;
    private String link_mp4;

    @OneToMany(mappedBy = "brano", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BranoAutore> braniAutori = new java.util.ArrayList<>();

    public java.util.Set<Autore> getAutori() {
        if (braniAutori == null) return java.util.Set.of();
        return braniAutori.stream()
                .map(BranoAutore::getAutore)
                .collect(java.util.stream.Collectors.toSet());
    }

    @OneToMany(mappedBy = "brano", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BranoPlaylist> braniPlaylist;

}
