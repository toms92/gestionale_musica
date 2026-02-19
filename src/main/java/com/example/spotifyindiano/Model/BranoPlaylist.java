package com.example.spotifyindiano.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "brani_playlist")
@IdClass(BranoPlaylistId.class)
public class BranoPlaylist {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_playlist", nullable = false)
    private Playlist playlist;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_brano", nullable = false)
    private Brano brano;

    @Column(name = "data_aggiunta_brano", nullable = false)
    private LocalDate dataAggiuntaBrano;
}

@Data
class BranoPlaylistId implements Serializable {
    private int playlist;
    private int brano;
}
