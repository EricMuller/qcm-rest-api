package com.emu.apps.qcm.services.repositories.custom;

import com.emu.apps.qcm.services.entity.common.BasicEntity;
import com.emu.apps.qcm.services.repositories.SimpleJpaBulkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Objects;


public class SimpleJpaBulkRepositoryImpl<T extends BasicEntity, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements SimpleJpaBulkRepository<T, ID> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final EntityManager entityManager;

    public SimpleJpaBulkRepositoryImpl(JpaEntityInformation entityInformation,
                                       EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public void bulkSave(Iterable<T> entities, int batchSize) {
        logger.warn("batchsize:" + String.valueOf(batchSize));
        int i = 0;
        for (T entity : entities) {
            persistOrMerge(entity);
            i++;
            if (i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }

    }

    private <T extends BasicEntity> T persistOrMerge(T t) {
        if (Objects.isNull(t.getId())) {
            entityManager.persist(t);
            return t;
        } else {
            return entityManager.merge(t);
        }
    }


}
