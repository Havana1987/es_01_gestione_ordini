package com.example.gestione_ordini.repository;

import com.example.gestione_ordini.repository.model.OrdineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdineRepository extends JpaRepository<OrdineEntity, UUID> {
}
