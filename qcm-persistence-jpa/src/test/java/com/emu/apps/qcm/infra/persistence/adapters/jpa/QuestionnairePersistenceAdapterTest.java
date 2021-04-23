package com.emu.apps.qcm.infra.persistence.adapters.jpa;

import com.emu.apps.qcm.domain.model.questionnaire.Questionnaire;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.DbFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.Fixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.fixtures.ModelFixture;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.repository.jql.QueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest(classes = SpringBootJpaTestConfig.class)
@ActiveProfiles(value = "test")
@Slf4j
class QuestionnairePersistenceAdapterTest {

    private final DbFixture dbFixture;

    private final ModelFixture modelFixture;

    private final QuestionnairePersistenceAdapter questionnairePersistenceAdapter;

    private final Javers javers;

    @Autowired
    QuestionnairePersistenceAdapterTest(DbFixture dbFixture, ModelFixture modelFixture, QuestionnairePersistenceAdapter questionnairePersistenceAdapter, Javers javers) {
        this.dbFixture = dbFixture;
        this.modelFixture = modelFixture;
        this.questionnairePersistenceAdapter = questionnairePersistenceAdapter;
        this.javers = javers;
    }

    @BeforeEach
    void setUp() {
        dbFixture.emptyDatabase();
    }

    @Test
    @Transactional
    void saveQuestionnaire() {

        Questionnaire questionnaire = modelFixture.createQuestionaire();

        Questionnaire questionnaire1 = questionnairePersistenceAdapter.saveQuestionnaire(questionnaire, Fixture.USER);

        Assertions.assertNotNull(questionnaire1);
        Assertions.assertEquals(questionnaire.getTitle(),questionnaire1.getTitle());
        Assertions.assertEquals(questionnaire.getDescription(),questionnaire1.getDescription());


        questionnaire1.setTitle("title 2");
        questionnaire1.setDescription("desc 2");

        questionnairePersistenceAdapter.saveQuestionnaire(questionnaire1, Fixture.USER);

        questionnaire1.setTitle("title 3");

        questionnairePersistenceAdapter.saveQuestionnaire(questionnaire1, Fixture.USER);

        questionnairePersistenceAdapter.saveQuestionnaire(questionnaire1, Fixture.USER);

        QueryBuilder jqlQuery = QueryBuilder.byClass(QuestionnaireEntity.class);
        List <CdoSnapshot> changes = javers.findSnapshots(jqlQuery.build());
        LOGGER.info(javers.getJsonConverter().toJson(changes));
    }
}
