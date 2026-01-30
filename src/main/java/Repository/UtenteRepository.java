package Repository;

import Model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    boolean existsByNomeUtenteAndPassword(String nomeUtente, String password);
    boolean addUtente(Utente utente);

}
