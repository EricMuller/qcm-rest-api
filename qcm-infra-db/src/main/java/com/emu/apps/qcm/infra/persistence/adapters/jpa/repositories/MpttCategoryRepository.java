package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.mptt.MpttCategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.mptt.MpttHierarchicalEntityRepositoryImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by eric on 05/06/2017.
 */
@Repository
public class MpttCategoryRepository extends MpttHierarchicalEntityRepositoryImpl <MpttCategoryEntity> {

    public MpttCategoryRepository() {
        super(MpttCategoryEntity.class);
    }

}
