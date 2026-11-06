package com.example.gestione_ordini.service.mapper;

import com.example.gestione_ordini.model.OrdineRequest;
import com.example.gestione_ordini.service.dto.OrdineRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MapperOrdineDto {
    @Mapping(
            target = "indirizzoSpedizione",
            expression = "java(mapIndirizzoSpedizione(request.getCittaSpedizione(), request.getCap()))")
    public abstract OrdineRequestDto toRequestDto (OrdineRequest request);

    public String mapIndirizzoSpedizione(String citta, String cap) {
        return citta + " - " + cap;
    }
}
