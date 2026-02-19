package com.example.spotifyindiano.Service;

import com.example.spotifyindiano.Exceptions.UserAlreadyCreatedException;
import com.example.spotifyindiano.Model.Utente;
import com.example.spotifyindiano.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository repository;

    public boolean checkCredenziali(String nomeUtente, String password) {
        return repository.existsByNomeUtenteAndPassword(nomeUtente, password);
    }

    public Utente getByNomeUtenteAndPassword(String nomeUtente, String password) {
        return repository.findByNomeUtenteAndPassword(nomeUtente, password);
    }

    public boolean addUtente(Utente utente) throws UserAlreadyCreatedException {
        if(utente.getNomeUtente() == null || utente.getNomeUtente().isBlank()){
            throw new IllegalArgumentException("Nome utente non valido");
        };
        if(utente.getPassword() == null || utente.getPassword().isBlank()){
            throw new IllegalArgumentException("Password non valida");
        }
        if(repository.existsByNomeUtente(utente.getNomeUtente())){
            throw new UserAlreadyCreatedException("Utente già esistente");
        };
        repository.save(utente);
        return true;
    }
}
