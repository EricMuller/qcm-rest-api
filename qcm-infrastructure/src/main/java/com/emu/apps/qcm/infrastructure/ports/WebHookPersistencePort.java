package com.emu.apps.qcm.infrastructure.ports;

import com.emu.apps.qcm.models.WebHookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface WebHookPersistencePort {

    WebHookDto saveWebHook(WebHookDto webhookDto, String principal);

    @Transactional(readOnly = true)
    Page <WebHookDto> findOneByUuid(Pageable pageable, String principal);

    WebHookDto findOneByUuid(String uuid);

    void deleteByUuid(String uuid);

}
