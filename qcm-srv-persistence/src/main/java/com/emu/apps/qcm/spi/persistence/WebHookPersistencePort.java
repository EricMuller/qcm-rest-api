package com.emu.apps.qcm.spi.persistence;

import com.emu.apps.qcm.aggregates.WebHook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface WebHookPersistencePort {

    WebHook saveWebHook(WebHook webhookDto, String principal);

    @Transactional(readOnly = true)
    Page <WebHook> findOneByUuid(Pageable pageable, String principal);

    WebHook findOneByUuid(String uuid);

    void deleteByUuid(String uuid);

}
