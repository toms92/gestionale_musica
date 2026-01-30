package Service;

import Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository repository;

    public boolean checkCredenziali(String nomeUtente, String password) {
        return repository.existsByNomeUtenteAndPassword(nomeUtente, password);
    }
}
