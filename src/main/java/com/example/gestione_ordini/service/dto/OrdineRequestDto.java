package com.example.gestione_ordini.service.dto;

import com.example.gestione_ordini.model.enums.Stato;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrdineRequestDto {
    private Long id;
    private String codiceOrdine;
    private LocalDateTime dataOrdine;
    private String nomeCliente;
    private String cognomeCliente;
    private String emailCliente;
    private String telefonoCliente;
    private String indirizzoSpedizione;
    private String cittaSpedizione;
    private String cap;
    private String prodotto;
    private Integer quantita;
    private Double prezzoUnitario;
    private Double totale;
    private Stato stato;
}
