package com.example.spotifyindiano.Repository;

import com.example.spotifyindiano.Model.Brano;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranoRepository extends JpaRepository<Brano, Long> {
    List<Brano> findAll();

    @Query("SELECT DISTINCT b FROM Brano b LEFT JOIN FETCH b.autori LEFT JOIN FETCH b.generi WHERE (:titolo IS NULL OR LOWER(b.titolo) LIKE LOWER(CONCAT('%', :titolo, '%'))) ")
    List<Brano> findByTitolo(@Param("titolo") String titolo, Sort sort);

    @Query("SELECT DISTINCT b FROM Brano b LEFT JOIN b.generi g LEFT JOIN FETCH b.autori WHERE (:titolo IS NULL OR LOWER(b.titolo) LIKE LOWER(CONCAT('%', :titolo, '%'))) ORDER BY g.nome ASC, b.titolo ASC")
    List<Brano> findByTitoloOrderByGenere(@Param("titolo") String titolo);
}
