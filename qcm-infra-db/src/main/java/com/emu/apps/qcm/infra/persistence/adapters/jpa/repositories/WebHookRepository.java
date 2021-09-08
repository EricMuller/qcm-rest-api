package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events.WebHookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface WebHookRepository extends PagingAndSortingRepository <WebHookEntity, Long> {


    Iterable <WebHookEntity> findPageByOwnerUuidEquals(UUID uuid);

    Page <WebHookEntity> findPageByOwnerUuidEquals(UUID uuid, Pageable pageable);

    Optional <WebHookEntity> findOneByUuidEquals(UUID uuid);

    void deleteByUuid(UUID uuid);
}
