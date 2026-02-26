package com.example.spotifyindiano.Controller;

import com.example.spotifyindiano.Model.Brano;
import com.example.spotifyindiano.Model.Utente;
import com.example.spotifyindiano.Service.BranoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BranoService branoService;

    private boolean isAdmin(HttpSession session) {
        Utente utente = (Utente) session.getAttribute("utente");
        return utente != null && utente.isAdmin();
    }

    @GetMapping("/brani")
    public String gestioneBrani(Model model, 
                                @RequestParam(value = "search", required = false) String search,
                                @RequestParam(value = "orderBy", required = false) String orderBy,
                                HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/index";
        }
        model.addAttribute("brani", branoService.getBraniFiltrati(search, orderBy));
        model.addAttribute("search", search);
        model.addAttribute("orderBy", orderBy);
        return "gestioneBrani";
    }

    @PostMapping("/brani/elimina/{id}")
    public String eliminaBrano(@PathVariable int id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/index";
        }
        branoService.deleteBrano(id);
        return "redirect:/admin/brani";
    }

    @GetMapping("/brani/aggiungi")
    public String formAggiungiBrano(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/index";
        }
        model.addAttribute("brano", new Brano());
        model.addAttribute("autori", branoService.getAllAutori());
        model.addAttribute("generi", branoService.getAllGeneri());
        return "aggiungiBrano";
    }

    @PostMapping("/brani/aggiungi")
    public String aggiungiBrano(@RequestParam String titolo,
                                @RequestParam String durata,
                                @RequestParam String link_yt,
                                @RequestParam(required = false) String link_mp4,
                                @RequestParam(required = false) List<Integer> idAutori,
                                @RequestParam(required = false) List<Integer> idGeneri,
                                HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/index";
        }
        Brano brano = new Brano();
        brano.setTitolo(titolo);
        try {
            brano.setDurata(java.sql.Time.valueOf(durata));
        } catch (Exception e) {
            // Se il formato è errato, proviamo a gestire HH:mm o altri casi semplici se necessario
            // Per ora assumiamo HH:mm:ss come da placeholder
            brano.setDurata(java.sql.Time.valueOf("00:00:00"));
        }
        brano.setLink_yt(link_yt);
        brano.setLink_mp4(link_mp4);

        branoService.addBrano(brano, idAutori, idGeneri);
        return "redirect:/admin/brani";
    }
}
