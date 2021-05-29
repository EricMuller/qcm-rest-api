package com.emu.apps.qcm.infra.persistence.adapters.jpa;


import com.emu.apps.qcm.domain.model.webhook.WebHook;
import com.emu.apps.qcm.infra.persistence.WebHookPersistencePort;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events.WebHookEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.AccountRepository;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories.WebHookRepository;
import com.emu.apps.qcm.infra.persistence.mappers.WebHookEntityMapper;
import com.emu.apps.shared.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.emu.apps.shared.exceptions.MessageSupport.UNKNOWN_UUID_USER;
import static com.emu.apps.shared.exceptions.MessageSupport.UNKNOWN_UUID_WEBHOOK;
import static java.util.Objects.nonNull;
import static java.util.UUID.fromString;


@Service
@Transactional
public class WebhookPersistenceAdapter implements WebHookPersistencePort {

    private final WebHookRepository webHookRepository;

    private final WebHookEntityMapper webHookMapper;

    private final AccountRepository userRepository;

    public WebhookPersistenceAdapter(WebHookRepository webHookRepository, WebHookEntityMapper webHookMapper, AccountRepository userRepository) {
        this.webHookRepository = webHookRepository;
        this.webHookMapper = webHookMapper;
        this.userRepository = userRepository;
    }


    @Override
    public WebHook saveWebHook(WebHook webHook, String principal) {

        WebHookEntity webHookEntity;
        if (nonNull(webHook.getId().toUuid())) {
            webHookEntity = webHookRepository.findOneByUuidEquals(fromString(webHook.getId().toUuid()))
                    .orElseThrow(() -> new EntityNotFoundException(webHook.getId().toUuid(), UNKNOWN_UUID_WEBHOOK));
            webHookMapper.dtoToModel(webHookEntity, webHook);
        } else {
            AccountEntity userEntity = userRepository.findByUuid(fromString(principal))
                    .orElseThrow(() -> new EntityNotFoundException(principal, UNKNOWN_UUID_USER));
            webHookEntity = webHookMapper.dtoToModel(webHook);
            webHookEntity.setUser(userEntity);
        }

        webHookEntity = nonNull(webHookEntity) ? webHookRepository.save(webHookEntity) : null;

        return webHookMapper.modelToDto(webHookEntity);
    }

    @Override
    public Page <WebHook> findOneByUuid(Pageable pageable, String principal) {
        return webHookMapper.pageToPageDto(webHookRepository.findPageByUserUuidEquals(fromString(principal), pageable));
    }

    @Override
    public WebHook findOneByUuid(String uuid) {
        return webHookMapper.modelToDto(webHookRepository.findOneByUuidEquals(fromString(uuid)).orElse(null));
    }

    @Override
    public void deleteByUuid(String uuid) {
        webHookRepository.deleteByUuid(fromString(uuid));
    }


}
