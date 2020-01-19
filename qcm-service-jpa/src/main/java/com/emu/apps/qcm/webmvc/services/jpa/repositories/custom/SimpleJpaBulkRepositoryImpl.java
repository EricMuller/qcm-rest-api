package com.emu.apps.qcm.webmvc.services.jpa.repositories.custom;

import com.emu.apps.qcm.webmvc.services.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.webmvc.services.jpa.repositories.SimpleJpaBulkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Objects;


public class SimpleJpaBulkRepositoryImpl<T extends AuditableEntity, I extends Serializable> extends SimpleJpaRepository<T, I> implements SimpleJpaBulkRepository<T, I> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final EntityManager entityManager;

    public SimpleJpaBulkRepositoryImpl(JpaEntityInformation entityInformation,
                                       EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public void bulkSave(Iterable<T> entities, int batchSize) {

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

    private <T extends AuditableEntity> T persistOrMerge(T t) {
        if (Objects.isNull(t.getId())) {
            entityManager.persist(t);
            return t;
        } else {
            return entityManager.merge(t);
        }
    }


}
