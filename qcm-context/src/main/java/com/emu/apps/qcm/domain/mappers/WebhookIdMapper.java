package com.emu.apps.qcm.domain.mappers;

import com.emu.apps.qcm.domain.model.webhook.WebhookId;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WebhookIdMapper {

    default WebhookId toWebhookId(UUID uuid) {
        return new WebhookId(uuid.toString());
    }

    default WebhookId toWebhookId(String uuid) {
        return new WebhookId(uuid);
    }

    default String webhookIdToUuid(WebhookId id) {
        return id.toUuid();
    }

}
