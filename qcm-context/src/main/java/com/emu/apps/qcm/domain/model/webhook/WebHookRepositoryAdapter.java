package com.emu.apps.qcm.domain.model.webhook;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.infra.persistence.WebHookPersistencePort;
import org.springframework.data.domain.Page;
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
class WebHookRepositoryAdapter implements WebHookRepository {


    private final WebHookPersistencePort webHookPersistencePort;

    public WebHookRepositoryAdapter(WebHookPersistencePort settingsPersistencePort) {
        this.webHookPersistencePort = settingsPersistencePort;
    }

    @Override
    public Page <WebHook> getWebHooks(Pageable pageable, PrincipalId principalId) {
        return this.webHookPersistencePort.findOneByUuid(pageable, principalId.toUuid());
    }

    @Override
    public void deleteWebHookByUuid(WebhookId webhookId) {
        webHookPersistencePort.deleteByUuid(webhookId.toUuid());
    }

    @Override
    public WebHook getWebHookByUuid(WebhookId webhookId) {
        return webHookPersistencePort.findOneByUuid(webhookId.toUuid());
    }

    @Override
    public WebHook saveWebHook(WebHook webHook, PrincipalId principalId) {
        return webHookPersistencePort.saveWebHook(webHook, principalId.toUuid());
    }
}
