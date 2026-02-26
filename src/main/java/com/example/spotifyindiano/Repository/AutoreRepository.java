package com.example.spotifyindiano.Repository;

import com.example.spotifyindiano.Model.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoreRepository extends JpaRepository<Autore, Long> {
}
