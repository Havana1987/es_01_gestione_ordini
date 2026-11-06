package com.example.gestione_ordini.service;

import com.example.gestione_ordini.exceptions.AppException;
import com.example.gestione_ordini.model.OrdineResponse;
import com.example.gestione_ordini.model.enums.Stato;
import com.example.gestione_ordini.repository.OrdineRepository;
import com.example.gestione_ordini.repository.model.OrdineEntity;
import com.example.gestione_ordini.service.dto.OrdineRequestDto;
import com.example.gestione_ordini.service.dto.StatisticheDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.gestione_ordini.model.enums.Stato.*;

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
        ordine.setStato(RICEVUTO);

        OrdineEntity ordineSalvato = ordineRepository.saveAndFlush(ordine);

        return new OrdineResponse(
                "Ordine salvato con successo",
                ordineSalvato.getId(),
                ordineSalvato.getCodiceOrdine(),
                ordineSalvato.getDataOrdine(),
                ordineSalvato.getTotale(),
                ordineSalvato.getStato());
    }

    public OrdineRequestDto getOrdineById(UUID id) {
        OrdineEntity ordineEntity = ordineRepository.findById(id)
                .orElseThrow(() -> new AppException("Ordine " + id + " not found", HttpStatus.NOT_FOUND));
        return convertToDto(ordineEntity);
    }

    public List<OrdineRequestDto> getOrders(String stato) {
        List<OrdineEntity> ordini;

        if (stato == null || stato.isEmpty()) {
            ordini = ordineRepository.findAll();
        } else {
            ordini = ordineRepository.findByStatus(stato);
        }

        return ordini.stream()
                .map(this::convertToDto)
                .toList();
    }

    public OrdineResponse updateOrder(UUID id, OrdineRequestDto newOrder) {
        return ordineRepository.findById(id).map(order -> {

            verificaCambioStato(order.getStato(), newOrder.getStato());

            order.setStato(newOrder.getStato());
            ordineRepository.save(order);

            return new OrdineResponse(
                    "Ordine aggiornato con successo",
                    order.getId(),
                    order.getCodiceOrdine(),
                    order.getDataOrdine(),
                    order.getTotale(),
                    order.getStato());
        }).orElseThrow(() -> new AppException("Ordine " + id + " not found", HttpStatus.NOT_FOUND));
    }

    public List<StatisticheDTO> statistiche() {
        return ordineRepository.countOrdiniPerStato();
    }

    private double calcolaTotale(double prezzoUni, int quantita) {
        return prezzoUni * quantita;
    }

    private String generaCodiceOrdine() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

    private OrdineRequestDto convertToDto(OrdineEntity entity) {
        OrdineRequestDto dto = new OrdineRequestDto();
        dto.setId(entity.getId());
        dto.setNomeCliente(entity.getNomeCliente());
        dto.setCognomeCliente(entity.getCognomeCliente());
        dto.setEmailCliente(entity.getEmailCliente());
        dto.setTelefonoCliente(entity.getTelefonoCliente());
        dto.setCittaSpedizione(entity.getCittaSpedizione());
        dto.setCap(entity.getCap());
        dto.setIndirizzoSpedizione(entity.getIndirizzoSpedizione());
        dto.setCodiceOrdine(entity.getCodiceOrdine());
        dto.setDataOrdine(entity.getDataOrdine());
        dto.setStato(entity.getStato());
        dto.setProdotto(entity.getProdotto());
        dto.setPrezzoUnitario(entity.getPrezzoUnitario());
        dto.setQuantita(entity.getQuantita());
        dto.setTotale(entity.getTotale());
        return dto;
    }

    public void verificaCambioStato(Stato statoAttuale, Stato nuovoStato) {
        boolean transizioneValida = switch (statoAttuale) {
            case RICEVUTO         -> nuovoStato == IN_LAVORAZIONE || nuovoStato == ANNULLATO;
            case IN_LAVORAZIONE -> nuovoStato == SPEDITO || nuovoStato == ANNULLATO;
            case SPEDITO, ANNULLATO -> false;
        };

        if (!transizioneValida) {
            throw new AppException("Transizione non consentita da " + statoAttuale + " a " + nuovoStato, HttpStatus.CONFLICT);
        }
    }
}
