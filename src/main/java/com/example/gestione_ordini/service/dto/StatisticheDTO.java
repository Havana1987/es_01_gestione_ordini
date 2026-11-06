package com.example.gestione_ordini.service.dto;

import lombok.Data;

@Data
public class StatisticheDTO {
    String stato;
    Long conteggio;

    public StatisticheDTO() {}

    public StatisticheDTO(String stato, Long conteggio) {
        this.stato = stato;
        this.conteggio = conteggio;
    }
}
