package com.example.gestione_ordini.model;

import com.example.gestione_ordini.model.enums.Stato;

import java.time.LocalDateTime;

public class OrdineResponse {
    private String message;
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
    private double prezzoUnitario;
    private double totale;
    private Stato stato;

    public OrdineResponse(String message, Long id, String codiceOrdine, LocalDateTime data, double totale, Stato statoIniziale) {
        this.message = message;
        this.id = id;
        this.codiceOrdine = codiceOrdine;
        this.dataOrdine = data;
        this.totale = totale;
        this.stato = statoIniziale;
    }

}
