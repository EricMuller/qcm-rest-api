package com.emu.apps.qcm.spi.persistence.adapters.jpa;


import com.emu.apps.qcm.api.models.WebHook;
import com.emu.apps.qcm.spi.persistence.WebHookPersistencePort;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.UserEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.events.WebHookEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.UserRepository;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.repositories.WebHookRepository;
import com.emu.apps.qcm.spi.persistence.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.spi.persistence.mappers.WebHookMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport.UNKNOWN_UUID_USER;
import static com.emu.apps.qcm.spi.persistence.exceptions.MessageSupport.UNKNOWN_UUID_WEBHOOK;

@Service
@Transactional
public class WebhookPersistenceAdapter implements WebHookPersistencePort {

    private final WebHookRepository webHookRepository;

    private final WebHookMapper webHookMapper;

    private final UserRepository userRepository;

    public WebhookPersistenceAdapter(WebHookRepository webHookRepository, WebHookMapper webHookMapper, UserRepository userRepository) {
        this.webHookRepository = webHookRepository;
        this.webHookMapper = webHookMapper;
        this.userRepository = userRepository;
    }


    @Override
    public WebHook saveWebHook(WebHook webhookDto, String principal) {

        WebHookEntity webHook;
        if (Objects.nonNull(webhookDto.getUuid())) {
            webHook = webHookRepository.findOneByUuidEquals(UUID.fromString(webhookDto.getUuid())).orElse(null);
            RaiseExceptionUtil.raiseIfNull(principal, webHook, UNKNOWN_UUID_WEBHOOK);
            webHookMapper.dtoToModel(webHook, webhookDto);
        } else {
            UserEntity user = userRepository.findByUuid(UUID.fromString(principal)).orElse(null);
            RaiseExceptionUtil.raiseIfNull(principal, user, UNKNOWN_UUID_USER);
            webHook = webHookMapper.dtoToModel(webhookDto);
            webHook.setUser(user);
        }

        webHook = Objects.nonNull(webHook) ? webHookRepository.save(webHook) : null;

        return webHookMapper.modelToDto(webHook);
    }

    @Override
    public Page <WebHook> findOneByUuid(Pageable pageable, String principal) {
        return webHookMapper.pageToPageDto(webHookRepository.findPageByUserUuidEquals(UUID.fromString(principal), pageable));
    }

    @Override
    public WebHook findOneByUuid(String uuid) {
        return webHookMapper.modelToDto(webHookRepository.findOneByUuidEquals(UUID.fromString(uuid)).orElse(null));
    }

    @Override
    public void deleteByUuid(String uuid) {
        webHookRepository.deleteByUuid(UUID.fromString(uuid));
    }


}
