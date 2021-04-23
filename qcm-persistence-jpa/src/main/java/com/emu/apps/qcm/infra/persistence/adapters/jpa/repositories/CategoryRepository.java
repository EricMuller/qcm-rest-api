package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.mptt.MpttHierarchicalEntityRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public class CategoryRepository extends MpttHierarchicalEntityRepositoryImpl <CategoryEntity> {

    public CategoryRepository() {
        super(CategoryEntity.class);
    }

}
