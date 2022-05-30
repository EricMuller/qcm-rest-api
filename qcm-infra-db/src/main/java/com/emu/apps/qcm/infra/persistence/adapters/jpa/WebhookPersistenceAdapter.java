package com.emu.apps.qcm.infra.persistence.adapters.jpa;


import com.emu.apps.qcm.domain.model.webhook.WebHook;
import com.emu.apps.qcm.infra.persistence.WebHookPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.account.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events.WebHookEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.AccountRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.WebHookRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.mappers.WebHookEntityMapper;
import com.emu.apps.shared.exceptions.I18nedNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_USER;
import static com.emu.apps.shared.exceptions.I18nedMessageSupport.UNKNOWN_UUID_WEBHOOK;
import static java.util.Objects.nonNull;
import static java.util.UUID.fromString;


@Service
@Transactional
public class WebhookPersistenceAdapter implements WebHookPersistencePort {

    private final WebHookRepository webHookRepository;

    private final WebHookEntityMapper webHookMapper;

    private final AccountRepository accountRepository;

    public WebhookPersistenceAdapter(WebHookRepository webHookRepository, WebHookEntityMapper webHookMapper, AccountRepository accountRepository) {
        this.webHookRepository = webHookRepository;
        this.webHookMapper = webHookMapper;
        this.accountRepository = accountRepository;
    }


    @Override
    public WebHook saveWebHook(WebHook webHook, String ownerId) {

        WebHookEntity webHookEntity;
        if (nonNull(webHook.getId().toUuid())) {
            webHookEntity = webHookRepository.findOneByUuidEquals(fromString(webHook.getId().toUuid()))
                    .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_WEBHOOK, webHook.getId().toUuid()));
            webHookMapper.modelToEntity(webHookEntity, webHook);
        } else {
            AccountEntity accountEntity = accountRepository.findById(fromString(ownerId))
                    .orElseThrow(() -> new I18nedNotFoundException(UNKNOWN_UUID_USER, ownerId ));
            webHookEntity = webHookMapper.modelToEntity(webHook);
            webHookEntity.setOwner(accountEntity);
        }

        webHookEntity = nonNull(webHookEntity) ? webHookRepository.save(webHookEntity) : null;

        return webHookMapper.entityToModel(webHookEntity);
    }

    @Override
    public Page <WebHook> findOneByUuid(Pageable pageable, String principal) {
        return webHookMapper.pageToPageDto(webHookRepository.findPageByOwnerUuidEquals(fromString(principal), pageable));
    }

    @Override
    public WebHook findOneByUuid(String uuid) {
        return webHookMapper.entityToModel(webHookRepository.findOneByUuidEquals(fromString(uuid)).orElse(null));
    }

    @Override
    public void deleteByUuid(String uuid) {
        webHookRepository.deleteByUuid(fromString(uuid));
    }


}
