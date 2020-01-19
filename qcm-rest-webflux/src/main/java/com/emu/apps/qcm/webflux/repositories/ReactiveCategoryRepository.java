package com.emu.apps.qcm.webflux.repositories;


import com.emu.apps.qcm.webflux.model.Category;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 *
 */
@Repository
public interface ReactiveCategoryRepository extends R2dbcRepository <Category, Long> {

    @Query("SELECT DISTINCT q FROM Category q WHERE q.libelle  = :libelle ")
    Mono <Category> findByLibelle(@Param("libelle") String libelle);
}
