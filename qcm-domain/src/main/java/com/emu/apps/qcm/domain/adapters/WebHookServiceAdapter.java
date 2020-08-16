package com.emu.apps.qcm.domain.adapters;

import com.emu.apps.qcm.api.models.WebHook;
import com.emu.apps.qcm.domain.ports.WebHookServicePort;
import com.emu.apps.qcm.spi.persistence.WebHookPersistencePort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Upload Business Delegate
 * <p>
 *
 * @author eric
 * @since 2.2.0
 */
@Service
public class WebHookServiceAdapter implements WebHookServicePort {


    private final WebHookPersistencePort webHookPersistencePort;

    public WebHookServiceAdapter(WebHookPersistencePort settingsPersistencePort) {
        this.webHookPersistencePort = settingsPersistencePort;
    }

    @Override
    public Iterable <WebHook> getWebHooks(Pageable pageable, String principal) {
       return  this.webHookPersistencePort.findOneByUuid(pageable, principal);
    }

    @Override
    public void deleteWebHookByUuid(String uuid) {
        webHookPersistencePort.deleteByUuid(uuid);
    }

    @Override
    public WebHook getWebHookByUuid(String uuid) {
        return webHookPersistencePort.findOneByUuid(uuid);
    }

    @Override
    public WebHook saveWebHook(WebHook webHookDto, String user) {
        return webHookPersistencePort.saveWebHook( webHookDto, user);
    }
}
