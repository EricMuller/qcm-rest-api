package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.mptt.MpttHierarchicalEntityRepositoryImpl;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import org.springframework.stereotype.Repository;

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public class CategoryRepository extends MpttHierarchicalEntityRepositoryImpl <CategoryEntity> {

    public CategoryRepository() {
        super(CategoryEntity.class);
    }

    //    @Query("SELECT DISTINCT q FROM Category q WHERE q.libelle  = :libelle ")
//    Category findByLibelle(@Param("libelle") String libelle);

//    Category findByTypeAndLibelle(Type type, String libelle);


}
