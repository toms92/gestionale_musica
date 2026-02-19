package com.example.spotifyindiano.Service;

import com.example.spotifyindiano.Model.Brano;
import com.example.spotifyindiano.Repository.BranoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranoService {
    @Autowired
    private BranoRepository branoRepository;

    public List<Brano> getBrani() {
        return branoRepository.findAll(Sort.by(Sort.Direction.ASC, "titolo")).stream().distinct().toList();
    }

    public List<Brano> getBraniFiltrati(String search, String orderBy) {
        String titolo = (search == null || search.isBlank()) ? null : search;
        List<Brano> brani;
        if ("durata_asc".equalsIgnoreCase(orderBy)) {
            brani = branoRepository.findByTitolo(titolo, Sort.by(Sort.Direction.ASC, "durata"));
        } else if ("durata_desc".equalsIgnoreCase(orderBy)) {
            brani = branoRepository.findByTitolo(titolo, Sort.by(Sort.Direction.DESC, "durata"));
        } else if ("genere".equalsIgnoreCase(orderBy)) {
            brani = branoRepository.findByTitoloOrderByGenere(titolo);
        } else {
            brani = branoRepository.findByTitolo(titolo, Sort.by(Sort.Direction.ASC, "titolo"));
        }
        return brani.stream().distinct().toList();
    }
}
