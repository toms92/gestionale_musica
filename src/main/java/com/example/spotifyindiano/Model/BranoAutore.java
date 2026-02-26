package com.example.spotifyindiano.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "brani_autori")
@IdClass(BranoAutoreId.class)
public class BranoAutore {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_brano", nullable = false)
    private Brano brano;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_autore", nullable = false)
    private Autore autore;

    @Column(name = "data_pubblicazione", nullable = false)
    private LocalDate dataPubblicazione;
}

@Data
class BranoAutoreId implements Serializable {
    private int brano;
    private int autore;
}
