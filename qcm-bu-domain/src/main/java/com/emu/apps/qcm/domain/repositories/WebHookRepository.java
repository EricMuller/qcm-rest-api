package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.WebHook;
import org.springframework.data.domain.Pageable;

public interface WebHookRepository {

    Iterable <WebHook> getWebHooks(Pageable pageable, String principal);

    void deleteWebHookByUuid(String uuid);

    WebHook getWebHookByUuid(String uuid);

    WebHook saveWebHook(WebHook webHookDto, String user);
}
