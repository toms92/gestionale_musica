package com.example.spotifyindiano.Service;

import com.example.spotifyindiano.Model.Autore;
import com.example.spotifyindiano.Model.Brano;
import com.example.spotifyindiano.Model.BranoAutore;
import com.example.spotifyindiano.Model.Genere;
import com.example.spotifyindiano.Repository.AutoreRepository;
import com.example.spotifyindiano.Repository.BranoRepository;
import com.example.spotifyindiano.Repository.GenereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BranoService {
    @Autowired
    private BranoRepository branoRepository;

    @Autowired
    private AutoreRepository autoreRepository;

    @Autowired
    private GenereRepository genereRepository;

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
        } else if ("titolo_desc".equalsIgnoreCase(orderBy)) {
            brani = branoRepository.findByTitolo(titolo, Sort.by(Sort.Direction.DESC, "titolo"));
        } else {
            brani = branoRepository.findByTitolo(titolo, Sort.by(Sort.Direction.ASC, "titolo"));
        }
        return brani.stream().distinct().toList();
    }

    @Transactional
    public void deleteBrano(int id) {
        branoRepository.deleteById((long) id);
    }

    @Transactional
    public void addBrano(Brano brano, List<Integer> idAutori, List<Integer> idGeneri) {
        if (idAutori != null) {
            Set<Autore> autori = autoreRepository.findAllById(idAutori.stream().map(Integer::longValue).collect(Collectors.toList())).stream().collect(Collectors.toSet());
            List<BranoAutore> braniAutori = autori.stream().map(a -> {
                BranoAutore ba = new BranoAutore();
                ba.setBrano(brano);
                ba.setAutore(a);
                ba.setDataPubblicazione(java.time.LocalDate.now());
                return ba;
            }).collect(Collectors.toList());
            brano.setBraniAutori(braniAutori);
        }
        if (idGeneri != null) {
            Set<Genere> generi = genereRepository.findAllById(idGeneri.stream().map(Integer::longValue).collect(Collectors.toList())).stream().collect(Collectors.toSet());
            brano.setGeneri(generi);
        }
        branoRepository.save(brano);
    }

    public List<Autore> getAllAutori() {
        return autoreRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public List<Genere> getAllGeneri() {
        return genereRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }
}
