package com.example.gestione_ordini.repository.model;

import com.example.gestione_ordini.model.enums.Stato;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordini")
@NoArgsConstructor
@Data
public class OrdineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codice_ordine")
    private String codiceOrdine;
    @Column(name = "data_ordine")
    private LocalDateTime dataOrdine;
    @Column(name = "nome_cliente")
    private String nomeCliente;
    @Column(name = "cognome_cliente")
    private String cognomeCliente;
    @Column(name = "email_cliente")
    private String emailCliente;
    @Column(name = "telefono_cliente")
    private String telefonoCliente;
    @Column(name = "indirizzo_spedizione")
    private String indirizzoSpedizione;
    @Column(name = "citta_spedizione")
    private String cittaSpedizione;
    @Column(name = "cap")
    private String cap;
    @Column(name = "prodotto")
    private String prodotto;
    @Column(name = "quantita")
    private Integer quantita;
    @Column(name = "prezzo_unitario")
    private double prezzoUnitario;
    @Column(name = "totale")
    private double totale;
    @Column(name = "stato")
    private Stato stato;
}
