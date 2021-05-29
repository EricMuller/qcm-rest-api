package com.emu.apps.qcm.domain.model.webhook;

import com.emu.apps.qcm.domain.model.base.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebHook extends DomainId<WebhookId> {

    private String url;

    private String secret;

    private Long defaultTimeOut;

}
