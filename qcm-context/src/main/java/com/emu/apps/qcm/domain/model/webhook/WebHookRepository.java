package com.emu.apps.qcm.domain.model.webhook;

import com.emu.apps.qcm.domain.model.base.PrincipalId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WebHookRepository {

    Page <WebHook> getWebHooks(Pageable pageable, PrincipalId principal);

    void deleteWebHookOfId(WebhookId webhookId);

    WebHook getWebHookOfId(WebhookId webhookId);

    WebHook saveWebHook(WebHook webHook, PrincipalId principal);
}
