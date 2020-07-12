package com.emu.apps.qcm.domain.ports;

import com.emu.apps.qcm.models.WebHookDto;
import org.springframework.data.domain.Pageable;

public interface WebHookServicePort {

    Iterable <WebHookDto> getWebHooks(Pageable pageable, String principal);

    void deleteWebHookByUuid(String uuid);

    WebHookDto getWebHookByUuid(String uuid);

    WebHookDto saveWebHook(WebHookDto webHookDto, String user);
}
