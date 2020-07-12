package com.emu.apps.qcm.infrastructure.adapters.jpa;


import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.settings.User;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.settings.WebHook;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.UserRepository;
import com.emu.apps.qcm.infrastructure.adapters.jpa.repositories.WebHookRepository;
import com.emu.apps.qcm.infrastructure.exceptions.MessageSupport;
import com.emu.apps.qcm.infrastructure.exceptions.RaiseExceptionUtil;
import com.emu.apps.qcm.infrastructure.ports.WebHookPersistencePort;
import com.emu.apps.qcm.mappers.WebHookMapper;
import com.emu.apps.qcm.models.WebHookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

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
    public WebHookDto saveWebHook(WebHookDto webhookDto, String principal) {

        WebHook webHook;
        if (Objects.nonNull(webhookDto.getUuid())) {
            webHook = webHookRepository.findOneByUuidEquals(UUID.fromString(webhookDto.getUuid())).orElse(null);
            RaiseExceptionUtil.raiseIfNull(principal, webHook, MessageSupport.UNKNOWN_UUID_WEBHOOK);
            webHookMapper.dtoToModel(webHook, webhookDto);
        } else {
            User user = userRepository.findByUuid(UUID.fromString(principal)).orElse(null);
            RaiseExceptionUtil.raiseIfNull(principal, user, MessageSupport.UNKNOWN_UUID_USER);
            webHook = webHookMapper.dtoToModel(webhookDto);
            webHook.setUser(user);
        }

        return webHookMapper.modelToDto(webHookRepository.save(webHook));
    }

    @Override
    public Page <WebHookDto> findOneByUuid(Pageable pageable, String principal) {
        return webHookMapper.pageToPageDto(webHookRepository.findPageByUser_UuidEquals(UUID.fromString(principal), pageable));
    }

    @Override
    public WebHookDto findOneByUuid(String uuid) {
        return webHookMapper.modelToDto(webHookRepository.findOneByUuidEquals(UUID.fromString(uuid)).orElse(null));
    }

    @Override
    public void deleteByUuid(String uuid){
        webHookRepository.deleteByUuid(UUID.fromString(uuid));
    }


}
