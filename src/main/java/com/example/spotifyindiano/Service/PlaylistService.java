package com.example.spotifyindiano.Service;

import com.example.spotifyindiano.Model.Playlist;
import com.example.spotifyindiano.Model.Brano;
import com.example.spotifyindiano.Model.BranoPlaylist;
import com.example.spotifyindiano.Repository.PlaylistRepository;
import com.example.spotifyindiano.Repository.BranoRepository;
import com.example.spotifyindiano.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository repository;

    @Autowired
    private BranoRepository branoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public List<Playlist> getAllPLaylists(){
        if(repository.findAll().isEmpty()){
            throw new IllegalStateException("Non ci sono playlist");
        }else{
            return repository.findAll();
        }
    }

    @Transactional
    public void createPlaylist(String nome, List<Integer> idBrani, com.example.spotifyindiano.Model.Utente utente) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della playlist è obbligatorio");
        }
        if (idBrani == null || idBrani.isEmpty()) {
            throw new IllegalArgumentException("Devi selezionare almeno un brano");
        }

        List<Brano> brani = branoRepository.findAllById(idBrani.stream().map(Integer::longValue).collect(Collectors.toList()));
        
        long totaleSecondi = 0;
        for (Brano b : brani) {
            Time durata = b.getDurata();
            if (durata != null) {
                String[] parts = durata.toString().split(":");
                int ore = Integer.parseInt(parts[0]);
                int minuti = Integer.parseInt(parts[1]);
                int secondi = Integer.parseInt(parts[2]);
                totaleSecondi += ore * 3600L + minuti * 60L + secondi;
            }
        }

        if (totaleSecondi > 90 * 60) {
            throw new IllegalArgumentException("La durata totale della playlist supera i 90 minuti");
        }

        Playlist p = new Playlist();
        p.setNome(nome);
        
        // Calcolo della durata totale da salvare
        long ore = totaleSecondi / 3600;
        long minuti = (totaleSecondi % 3600) / 60;
        long secondi = totaleSecondi % 60;
        p.setDurata(Time.valueOf(String.format("%02d:%02d:%02d", ore, minuti, secondi)));
        p.setData_creazione(new java.sql.Date(System.currentTimeMillis()));
        p.setUtente(utente);

        // Associazione brani-playlist con la data di aggiunta
        List<BranoPlaylist> braniPlaylist = brani.stream().map(b -> {
            BranoPlaylist bp = new BranoPlaylist();
            bp.setPlaylist(p);
            bp.setBrano(b);
            bp.setDataAggiuntaBrano(java.time.LocalDate.now());
            return bp;
        }).collect(Collectors.toList());
        
        p.setBraniPlaylist(braniPlaylist);

        repository.save(p);
    }
}
