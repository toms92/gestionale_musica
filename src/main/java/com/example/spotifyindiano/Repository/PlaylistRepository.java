package com.example.spotifyindiano.Repository;

import com.example.spotifyindiano.Model.Brano;
import com.example.spotifyindiano.Model.Playlist;
import com.example.spotifyindiano.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findAll();
    Playlist findByNome(String nome);
    boolean existsByNome(String nome);

}
