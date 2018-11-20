package com.emu.apps.qcm.services.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface SimpleJpaBulkRepository<T, Id extends Serializable>
  extends JpaRepository<T, Id> {

  void bulkSave(Iterable<T> entities, int batchSize);
}