package com.emu.apps.qcm.domain.model.webhook;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import com.emu.apps.qcm.domain.model.webhook.WebHook;
import com.emu.apps.qcm.domain.model.webhook.WebhookId;
import com.emu.apps.qcm.domain.model.webhook.WebHookRepository;
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
public class WebHookRepositoryAdapter implements WebHookRepository {


    private final WebHookPersistencePort webHookPersistencePort;

    public WebHookRepositoryAdapter(WebHookPersistencePort settingsPersistencePort) {
        this.webHookPersistencePort = settingsPersistencePort;
    }

    @Override
    public Page <WebHook> getWebHooks(Pageable pageable, PrincipalId principal) {
        return this.webHookPersistencePort.findOneByUuid(pageable, principal.toUUID());
    }

    @Override
    public void deleteWebHookByUuid(WebhookId webhookId) {
        webHookPersistencePort.deleteByUuid(webhookId.toUUID());
    }

    @Override
    public WebHook getWebHookByUuid(WebhookId webhookId) {
        return webHookPersistencePort.findOneByUuid(webhookId.toUUID());
    }

    @Override
    public WebHook saveWebHook(WebHook webHook, String user) {
        return webHookPersistencePort.saveWebHook(webHook, user);
    }
}
