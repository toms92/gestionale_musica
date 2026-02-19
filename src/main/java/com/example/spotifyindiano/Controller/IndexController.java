package com.example.spotifyindiano.Controller;

import com.example.spotifyindiano.Service.BranoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    BranoService branoService;

    @GetMapping(value = {"/index", "/"})
    public String index(Model model){
        model.addAttribute("brani", branoService.getBrani());
        return "index";
    }

}
