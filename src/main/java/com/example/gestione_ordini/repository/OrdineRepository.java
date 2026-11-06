package com.example.gestione_ordini.repository;

import com.example.gestione_ordini.repository.model.OrdineEntity;
import com.example.gestione_ordini.service.dto.StatisticheDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrdineRepository extends JpaRepository<OrdineEntity, UUID> {

    @Query("SELECT o FROM OrdineEntity o WHERE o.stato = :stato")
    List<OrdineEntity> findByStatus(@Param("stato") String stato);

    @Query("SELECT new com.example.gestione_ordini.service.dto.StatisticheDTO(str(o.stato), COUNT(o)) " +
            "FROM OrdineEntity o " +
            "GROUP BY o.stato")
    List<StatisticheDTO> countOrdiniPerStato();
}
