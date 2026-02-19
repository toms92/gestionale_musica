package com.example.spotifyindiano.Controller;

import com.example.spotifyindiano.Model.Utente;
import com.example.spotifyindiano.Service.UtenteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public String login(@RequestParam String nomeUtente, @RequestParam String password, HttpSession session) {
        Utente utente = utenteService.getByNomeUtenteAndPassword(nomeUtente, password);
        if (utente != null) {
            session.setAttribute("utente", utente);
            return "redirect:/index";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("utente") != null) {
            return "redirect:/index";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
