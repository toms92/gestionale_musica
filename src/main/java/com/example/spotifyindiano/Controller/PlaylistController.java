package com.example.spotifyindiano.Controller;

import com.example.spotifyindiano.Model.Utente;
import com.example.spotifyindiano.Service.BranoService;
import com.example.spotifyindiano.Service.PlaylistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import com.example.spotifyindiano.Model.Playlist;

@Controller
public class PlaylistController {

    @Autowired
    private BranoService branoService;
    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/playlist")
    public String playlist(Model model, HttpSession session){
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null) {
            return "redirect:/login";
        }
        try {
            model.addAttribute("playlist", playlistService.getAllPLaylists()); // Nota: qui andrebbero filtrate per utente se necessario
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "playlist";
    }

    @GetMapping("/playlist/{id}")
    public String dettaglioPlaylist(@PathVariable int id, 
                                    @RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "orderBy", required = false) String orderBy,
                                    Model model, HttpSession session) {
        if (session.getAttribute("utente") == null) {
            return "redirect:/login";
        }
        try {
            Playlist playlist = playlistService.getPlaylistByIdFiltrata(id, search, orderBy);
            model.addAttribute("p", playlist);
            model.addAttribute("search", search);
            model.addAttribute("orderBy", orderBy);
            return "playlistDettaglio";
        } catch (IllegalArgumentException e) {
            return "redirect:/playlist";
        }
    }

    @PostMapping("/playlist/{id}/rimuovi/{branoId}")
    public String rimuoviBrano(@PathVariable int id, @PathVariable int branoId, HttpSession session) {
        if (session.getAttribute("utente") == null) {
            return "redirect:/login";
        }
        playlistService.rimuoviBranoDaPlaylist(id, branoId);
        return "redirect:/playlist/" + id;
    }

    @GetMapping("/playlist/crea")
    public String mostraCreaPlaylist(Model model,
                                     @RequestParam(value = "search", required = false) String search,
                                     @RequestParam(value = "orderBy", required = false) String orderBy,
                                     HttpSession session) {
        if (session.getAttribute("utente") == null) {
            return "redirect:/login";
        }
        model.addAttribute("brani", branoService.getBraniFiltrati(search, orderBy));
        model.addAttribute("search", search);
        model.addAttribute("orderBy", orderBy);
        return "creaPlaylist";
    }

    @PostMapping("/playlist/crea")
    public String creaPlaylist(@RequestParam String nome, @RequestParam(required = false) List<Integer> idBrani, Model model, HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utente");
        if (utente == null) {
            return "redirect:/login";
        }
        try {
            playlistService.createPlaylist(nome, idBrani, utente);
            return "redirect:/playlist";
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("brani", branoService.getBrani());
            return "creaPlaylist";
        }
    }

}
