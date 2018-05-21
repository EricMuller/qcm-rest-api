package com.emu.apps.qcm.services.repositories;

import com.emu.apps.qcm.services.entity.epics.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public interface EpicRepository extends PagingAndSortingRepository<Category, Long> {

    @Query("SELECT DISTINCT q FROM Category q WHERE q.libelle  = :libelle ")
    Category findByLibelle(@Param("libelle") String libelle);

}
