package com.example.gestione_ordini.controller;

import com.example.gestione_ordini.model.OrdineRequest;
import com.example.gestione_ordini.model.OrdineResponse;
import com.example.gestione_ordini.service.OrdineService;
import com.example.gestione_ordini.service.dto.OrdineRequestDto;
import com.example.gestione_ordini.service.mapper.MapperOrdineDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GestioneOrdiniController {

    private static final Logger log = LoggerFactory.getLogger(GestioneOrdiniController.class);
    private final MapperOrdineDto mapperOrderDto;
    private final OrdineService service;

    @PostMapping("/createOrder")
    public OrdineResponse createOrder(@Valid @RequestBody OrdineRequest order) {
        OrdineRequestDto requestDto = mapperOrderDto.toRequestDto(order);
        return service.createOrdine(requestDto);
    }
}
