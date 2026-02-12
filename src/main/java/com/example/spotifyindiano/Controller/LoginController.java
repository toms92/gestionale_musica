package com.example.spotifyindiano.Controller;

import com.example.spotifyindiano.Service.UtenteService;
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
    public String login(@RequestParam String nomeUtente, @RequestParam String password) {
        return utenteService.checkCredenziali(nomeUtente, password)
            ? "redirect:/index"
            : "redirect:/login?error";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
