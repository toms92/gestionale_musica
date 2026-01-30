package Controller;

import Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SignupController {
    @Autowired
    private UtenteService utenteService;


}
