package com.example.gestione_ordini.controller;

import com.example.gestione_ordini.model.OrdineRequest;
import com.example.gestione_ordini.model.OrdineResponse;
import com.example.gestione_ordini.service.OrdineService;
import com.example.gestione_ordini.service.dto.OrdineRequestDto;
import com.example.gestione_ordini.service.dto.StatisticheDTO;
import com.example.gestione_ordini.service.mapper.MapperOrdineDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GestioneOrdiniController {

    private static final Logger log = LoggerFactory.getLogger(GestioneOrdiniController.class);
    private final MapperOrdineDto mapperOrderDto;
    private final OrdineService service;

    @PostMapping("/ordini")
    public OrdineResponse createOrder(@Valid @RequestBody OrdineRequest order) {
        OrdineRequestDto requestDto = mapperOrderDto.toRequestDto(order);
        return service.createOrdine(requestDto);
    }

    @GetMapping("/ordini/{id}")
    public ResponseEntity<Object> getOrdineById(@Valid @PathVariable("id") String id) {

        if (id == null || id.isBlank()) {
            return ResponseEntity.badRequest().body("ID mancante");
        }

        UUID uuidRicercato;
        try {
            uuidRicercato = UUID.fromString(id);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body("Formato ID non valido");
        }

        OrdineRequestDto ordine = service.getOrdineById(uuidRicercato);

        return ResponseEntity.ok().body(ordine);
    }

    @GetMapping("/ordini")
    public ResponseEntity<List<OrdineRequestDto>> getAllOrders(
            @Valid @RequestParam(required = false) String stato
    ) {
        List<OrdineRequestDto> orders = service.getOrders(stato);

        return ResponseEntity.ok().body(orders);
    }

    @PatchMapping("/ordini/{id}")
    public ResponseEntity<Object> updateOrder(
            @PathVariable("id") String id,
            @Valid @RequestBody OrdineRequest orderRequest) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Formato ID non valido");
        }
        OrdineRequestDto orderRequestDto = mapperOrderDto.toRequestDto(orderRequest);
        OrdineResponse orderResponseDto = service.updateOrder(uuid, orderRequestDto);

        return ResponseEntity.ok().body(orderResponseDto);
    }

    @GetMapping("ordini/statistiche/stato")
    public ResponseEntity<List<StatisticheDTO>> getStatistichePerStato(){
        List<StatisticheDTO> statistiche = service.statistiche();

        return ResponseEntity.ok().body(statistiche);
    }

}
