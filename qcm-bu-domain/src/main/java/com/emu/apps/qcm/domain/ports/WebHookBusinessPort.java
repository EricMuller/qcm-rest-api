package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.api.models.WebHook;
import org.springframework.data.domain.Pageable;

public interface WebHookBusinessPort {

    Iterable <WebHook> getWebHooks(Pageable pageable, String principal);

    void deleteWebHookByUuid(String uuid);

    WebHook getWebHookByUuid(String uuid);

    WebHook saveWebHook(WebHook webHookDto, String user);
}
