package com.example.spotifyindiano.Repository;

import com.example.spotifyindiano.Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    boolean existsByNomeUtenteAndPassword(String nomeUtente, String password);
    Utente findByNomeUtenteAndPassword(String nomeUtente, String password);
    boolean existsByNomeUtente(String nomeUtente);

}
