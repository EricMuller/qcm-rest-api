package com.emu.apps.qcm.infra.persistence.adapters.jpa.repositories;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.BaeldungPostgresqlExtension;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.events.WebHookEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
@ContextConfiguration(initializers = {WebhookRepositoryTest.Initializer.class})
public class WebhookRepositoryTest {

    @RegisterExtension
    static BaeldungPostgresqlExtension postgresqlContainer = BaeldungPostgresqlExtension.getInstance();

    static class Initializer
            implements ApplicationContextInitializer <ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgresqlContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgresqlContainer.getUsername(),
                    "spring.datasource.password=" + postgresqlContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Autowired
    private DbFixture dbFixture;

    @Autowired
    private WebHookRepository webHookRepository;

    @Test
    @Transactional
    public void findAllByUser_UuidEqualsTest() {

        final UUID uuid = UUID.randomUUID();

        AccountEntity owner = dbFixture.createAccount("user.test.fr", uuid);

        WebHookEntity webhook = dbFixture.createWebhook(owner, uuid.toString());

        assertNotNull(webhook.getId());

        Iterable <WebHookEntity> question = webHookRepository.findPageByOwnerUuidEquals(owner.getUuid());

        WebHookEntity webhook1 = StreamSupport.stream(question.spliterator(), false).findFirst().orElse(null);

        Assertions.assertNotNull(webhook1);

        assertEquals("user.test.fr", webhook1.getOwner().getEmail());

    }

    @Test
    @Transactional
    public void findPagByUser_UuidEqualsTest() {

        final UUID uuid = UUID.randomUUID();

        AccountEntity owner = dbFixture.createAccount("user.test.fr", uuid);

        WebHookEntity webhook = dbFixture.createWebhook(owner, uuid.toString());

        assertNotNull(webhook.getId());

        Page <WebHookEntity> question = webHookRepository.findPageByOwnerUuidEquals(owner.getUuid(), PageRequest.of(0, 10));

        WebHookEntity webhook1 = StreamSupport.stream(question.spliterator(), false).findFirst().orElse(null);

        Assertions.assertNotNull(webhook1);

        assertEquals("user.test.fr", webhook1.getOwner().getEmail());

    }

    @Test
    @Transactional
    public void findAllByUuidTest() {

        final UUID uuid = UUID.randomUUID();

        AccountEntity owner = dbFixture.createAccount("user.test.fr", uuid);

        WebHookEntity webhook = dbFixture.createWebhook(owner, uuid.toString());

        assertNotNull(webhook.getId());

        WebHookEntity webhook1 = webHookRepository.findOneByUuidEquals(webhook.getUuid()).orElse(null);

        Assertions.assertNotNull(webhook1);

        assertEquals("user.test.fr", webhook1.getOwner().getEmail());

    }

}
