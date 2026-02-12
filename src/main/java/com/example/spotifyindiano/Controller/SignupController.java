package com.example.spotifyindiano.Controller;

import com.example.spotifyindiano.Exceptions.UserAlreadyCreatedException;
import com.example.spotifyindiano.Model.Utente;
import com.example.spotifyindiano.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SignupController {
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/signup")
    public String add_account(@ModelAttribute("utente") Utente utente, Model model) {
        try {
            utenteService.addUtente(utente);
            return "redirect:/index";
        } catch (UserAlreadyCreatedException | IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("utente", utente); // mantiene i valori inseriti
            return "signup";
        }
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "signup";
    }
}
