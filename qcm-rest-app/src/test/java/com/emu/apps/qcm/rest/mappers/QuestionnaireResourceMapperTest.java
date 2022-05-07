package com.emu.apps.qcm.rest.mappers;

import com.emu.apps.qcm.domain.mappers.*;
import com.emu.apps.qcm.rest.controllers.management.resources.AccountResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {QuestionnaireResourceMapperImpl.class, QuestionIdMapperImpl.class,
        QuestionnaireIdMapperImpl.class, AccountIdMapperImpl.class, CategoryIdMapperImpl.class,
        WebhookIdMapperImpl.class, TagIdMapperImpl.class})
@Tag("MapstructTest")
class QuestionnaireResourceMapperTest {


    @Autowired
    private QuestionnaireResourceMapper questionnaireResourceMapper;

    @Test
    void testAccountToModel() {

        var uuid = UUID.randomUUID().toString();
        var resource = new AccountResource();
        resource.setUuid(uuid);

        var account = questionnaireResourceMapper.accountToModel(resource);

        Assertions.assertNotNull(account);

        Assertions.assertNotNull(account.getId(),"uuid should not be Null");

        Assertions.assertEquals(uuid,account.getId().toUuid());

    }


}
