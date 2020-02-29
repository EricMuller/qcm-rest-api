package com.emu.apps.qcm.services.jpa.repositories.category;

import com.emu.apps.qcm.services.entity.category.Category;
import com.emu.apps.qcm.services.entity.category.Category.Type;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public interface CategoryRepository extends PagingAndSortingRepository <Category, Long>, JpaSpecificationExecutor <Category> {

    @Query("SELECT DISTINCT q FROM Category q WHERE q.libelle  = :libelle ")
    Category findByLibelle(@Param("libelle") String libelle);

    Category findByLibelleAndType(String libelle, Type type);
}
