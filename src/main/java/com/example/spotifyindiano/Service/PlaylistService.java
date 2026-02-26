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

    @Transactional(readOnly = true)
    public Playlist getPlaylistById(int id) {
        Playlist p = repository.findById((long) id).orElseThrow(() -> new IllegalArgumentException("Playlist non trovata"));
        if (p.getBraniPlaylist() != null) {
            p.getBraniPlaylist().forEach(bp -> {
                if (bp.getBrano() != null) {
                    if (bp.getBrano().getAutori() != null) bp.getBrano().getAutori().size();
                    if (bp.getBrano().getGeneri() != null) bp.getBrano().getGeneri().size();
                }
            });
        }
        return p;
    }

    @Transactional(readOnly = true)
    public Playlist getPlaylistByIdFiltrata(int id, String search, String orderBy) {
        Playlist p = getPlaylistById(id);
        
        List<BranoPlaylist> braniFiltrati = p.getBraniPlaylist();
        
        // Ricerca per nome
        if (search != null && !search.isBlank()) {
            String searchLower = search.toLowerCase();
            braniFiltrati = braniFiltrati.stream()
                .filter(bp -> bp.getBrano().getTitolo().toLowerCase().contains(searchLower))
                .collect(Collectors.toList());
        }
        
        // Ordinamento (usiamo lo stesso schema di BranoService)
        if (orderBy != null) {
            switch (orderBy.toLowerCase()) {
                case "durata_asc":
                    braniFiltrati.sort((bp1, bp2) -> bp1.getBrano().getDurata().compareTo(bp2.getBrano().getDurata()));
                    break;
                case "durata_desc":
                    braniFiltrati.sort((bp1, bp2) -> bp2.getBrano().getDurata().compareTo(bp1.getBrano().getDurata()));
                    break;
                case "titolo_desc":
                    braniFiltrati.sort((bp1, bp2) -> bp2.getBrano().getTitolo().compareToIgnoreCase(bp1.getBrano().getTitolo()));
                    break;
                case "genere":
                    // Per il genere prendiamo il primo se presente
                    braniFiltrati.sort((bp1, bp2) -> {
                        String g1 = bp1.getBrano().getGeneri().isEmpty() ? "" : bp1.getBrano().getGeneri().iterator().next().getNome();
                        String g2 = bp2.getBrano().getGeneri().isEmpty() ? "" : bp2.getBrano().getGeneri().iterator().next().getNome();
                        int res = g1.compareToIgnoreCase(g2);
                        return res != 0 ? res : bp1.getBrano().getTitolo().compareToIgnoreCase(bp2.getBrano().getTitolo());
                    });
                    break;
                case "titolo_asc":
                default:
                    braniFiltrati.sort((bp1, bp2) -> bp1.getBrano().getTitolo().compareToIgnoreCase(bp2.getBrano().getTitolo()));
                    break;
            }
        } else {
             braniFiltrati.sort((bp1, bp2) -> bp1.getBrano().getTitolo().compareToIgnoreCase(bp2.getBrano().getTitolo()));
        }
        
        p.setBraniPlaylist(braniFiltrati);
        return p;
    }

    @Transactional
    public void rimuoviBranoDaPlaylist(int playlistId, int branoId) {
        Playlist p = repository.findById((long) playlistId).orElseThrow(() -> new IllegalArgumentException("Playlist non trovata"));
        
        // Rimuoviamo il BranoPlaylist dalla lista
        boolean removed = p.getBraniPlaylist().removeIf(bp -> bp.getBrano().getId() == branoId);
        
        if (removed) {
            // Ricalcoliamo la durata totale
            long totaleSecondi = 0;
            for (BranoPlaylist bp : p.getBraniPlaylist()) {
                Time durata = bp.getBrano().getDurata();
                if (durata != null) {
                    String[] parts = durata.toString().split(":");
                    int ore = Integer.parseInt(parts[0]);
                    int minuti = Integer.parseInt(parts[1]);
                    int secondi = Integer.parseInt(parts[2]);
                    totaleSecondi += ore * 3600L + minuti * 60L + secondi;
                }
            }
            long ore = totaleSecondi / 3600;
            long minuti = (totaleSecondi % 3600) / 60;
            long secondi = totaleSecondi % 60;
            p.setDurata(Time.valueOf(String.format("%02d:%02d:%02d", ore, minuti, secondi)));
            
            repository.save(p);
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
