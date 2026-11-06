package com.example.gestione_ordini.service;

import com.example.gestione_ordini.model.OrdineResponse;
import com.example.gestione_ordini.model.enums.Stato;
import com.example.gestione_ordini.repository.OrdineRepository;
import com.example.gestione_ordini.repository.model.OrdineEntity;
import com.example.gestione_ordini.service.dto.OrdineRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdineService {
    private final OrdineRepository ordineRepository;

    public OrdineResponse createOrdine(OrdineRequestDto ordineRequestDto){
        OrdineEntity ordine = new OrdineEntity();

        ordine.setCodiceOrdine(generaCodiceOrdine());
        ordine.setDataOrdine(LocalDateTime.now());
        ordine.setNomeCliente(ordineRequestDto.getNomeCliente());
        ordine.setCognomeCliente(ordineRequestDto.getCognomeCliente());
        ordine.setEmailCliente(ordineRequestDto.getEmailCliente());
        ordine.setTelefonoCliente(ordineRequestDto.getTelefonoCliente());
        ordine.setIndirizzoSpedizione(ordineRequestDto.getIndirizzoSpedizione());
        ordine.setCittaSpedizione(ordineRequestDto.getCittaSpedizione());
        ordine.setCap(ordineRequestDto.getCap());
        ordine.setProdotto(ordineRequestDto.getProdotto());
        ordine.setQuantita(ordineRequestDto.getQuantita());
        ordine.setPrezzoUnitario(ordineRequestDto.getPrezzoUnitario());
        ordine.setTotale(calcolaTotale(ordineRequestDto.getPrezzoUnitario(), ordineRequestDto.getQuantita()));
        ordine.setStato(Stato.RICEVUTO);

        OrdineEntity ordineSalvato = ordineRepository.saveAndFlush(ordine);

        return new OrdineResponse(
                "Ordine salvato con successo",
                ordineSalvato.getId(),
                ordineSalvato.getCodiceOrdine(),
                ordineSalvato.getDataOrdine(),
                ordineSalvato.getTotale(),
                ordineSalvato.getStato());
    }

    private double calcolaTotale(double prezzoUni, int quantita) {
        return prezzoUni * quantita;
    }

    private String generaCodiceOrdine() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }
}
