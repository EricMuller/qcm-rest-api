package com.emu.apps.qcm.domain.repositories;

import com.emu.apps.qcm.domain.models.base.PrincipalId;
import com.emu.apps.qcm.domain.models.webhook.WebHook;
import com.emu.apps.qcm.domain.models.webhook.WebhookId;
import org.springframework.data.domain.Pageable;

public interface WebHookRepository {

    Iterable <WebHook> getWebHooks(Pageable pageable, PrincipalId principal);

    void deleteWebHookByUuid(WebhookId webhookId);

    WebHook getWebHookByUuid(WebhookId webhookId);

    WebHook saveWebHook(WebHook webHook, String user);
}
