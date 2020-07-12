package com.emu.apps.qcm.infrastructure.adapters.jpa.repositories;

import com.emu.apps.qcm.infrastructure.DbFixture;
import com.emu.apps.qcm.infrastructure.adapters.jpa.config.SpringBootTestConfig;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.settings.User;
import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.settings.WebHook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SpringBootTestConfig.class)
@ActiveProfiles(value = "test")
public class WebhookRepositoryTest {

    @Autowired
    private DbFixture fixture;

    @Autowired
    private WebHookRepository webHookRepository;

    @Test
    @Transactional
    public void findAllByUser_UuidEqualsTest() {

        User user = fixture.createUser("eee.test.fr");

        WebHook webhook = fixture.createWebhook(user);

        assertNotNull(webhook.getId());

        Iterable <WebHook> question = webHookRepository.findPageByUser_UuidEquals(user.getUuid());

        WebHook webhook1 = StreamSupport.stream(question.spliterator(), false).findFirst().orElse(null);

        Assertions.assertNotNull(webhook1);

        assertEquals("eee.test.fr", webhook1.getUser().getEmail());

    }

    @Test
    @Transactional
    public void findPagByUser_UuidEqualsTest() {

        User user = fixture.createUser("eee.test.fr");

        WebHook webhook = fixture.createWebhook(user);

        assertNotNull(webhook.getId());

        Page <WebHook> question = webHookRepository.findPageByUser_UuidEquals(user.getUuid(),PageRequest.of(0,10));

        WebHook webhook1 = StreamSupport.stream(question.spliterator(), false).findFirst().orElse(null);

        Assertions.assertNotNull(webhook1);

        assertEquals("eee.test.fr", webhook1.getUser().getEmail());

    }

    @Test
    @Transactional
    public void findAllByUuidTest() {

        User user = fixture.createUser("eee.test.fr");

        WebHook webhook = fixture.createWebhook(user);

        assertNotNull(webhook.getId());

        WebHook webhook1 = webHookRepository.findOneByUuidEquals(webhook.getUuid()).orElse(null);

        Assertions.assertNotNull(webhook1);

        assertEquals("eee.test.fr", webhook1.getUser().getEmail());

    }

}
