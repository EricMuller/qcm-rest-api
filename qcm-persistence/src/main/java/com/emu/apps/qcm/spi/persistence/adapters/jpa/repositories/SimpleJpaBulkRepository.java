package com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface SimpleJpaBulkRepository<T, I extends Serializable>
        extends JpaRepository <T, I> {

    void bulkSave(Iterable <T> entities, int batchSize);
}
