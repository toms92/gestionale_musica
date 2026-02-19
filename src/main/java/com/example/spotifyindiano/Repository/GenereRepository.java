package com.example.spotifyindiano.Repository;

import com.example.spotifyindiano.Model.Genere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenereRepository extends JpaRepository<Genere, Long> {
}
