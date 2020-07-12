package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.settings.WebHook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface WebHookRepository extends PagingAndSortingRepository <WebHook, Long> {


    Iterable <WebHook> findPageByUser_UuidEquals(UUID uuid);

    Page <WebHook> findPageByUser_UuidEquals(UUID uuid, Pageable pageable);

    Optional <WebHook> findOneByUuidEquals(UUID uuid);

    void deleteByUuid(UUID uuid);
}
