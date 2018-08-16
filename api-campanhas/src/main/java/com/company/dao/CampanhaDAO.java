package com.company.dao;

import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.repository.ArangoRepository;
import com.company.entity.Campanha;

import java.time.LocalDate;
import java.util.List;

public interface CampanhaDAO extends ArangoRepository<Campanha> {

    @Query("FOR c IN campanhas FILTER c.`inicio` <= @0 AND c.`fim` >= @0 RETURN c")
    List<Campanha> findActiveInDate(LocalDate date);

    @Query("FOR c IN campanhas FILTER c.`inicio` <= @0 AND c.`fim` >= @1 RETURN c")
    List<Campanha> findInBetween(LocalDate inicio, LocalDate fim);

}