package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.mptt.MpttHierarchicalEntityRepositoryImpl;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.category.Category;
import org.springframework.stereotype.Repository;

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public class CategoryRepository extends MpttHierarchicalEntityRepositoryImpl <Category> {

    public CategoryRepository() {
        super(Category.class);
    }

    //    @Query("SELECT DISTINCT q FROM Category q WHERE q.libelle  = :libelle ")
//    Category findByLibelle(@Param("libelle") String libelle);

//    Category findByTypeAndLibelle(Type type, String libelle);


}
