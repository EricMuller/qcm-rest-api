package com.emu.apps.qcm.spi.infrastructure;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.config.SpringBootJpaTestConfig;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public  class Fixture {
    public static final String QUESTION_QUESTION_1 = "a cool question";

    public static final String QUESTION_QUESTION_2 = "a cool question 2";

    public static final String RESPONSE_RESPONSE_1 = "a cool response 1";

    public static final String RESPONSE_RESPONSE_2 = "a cool response 2";

    public static final String QUESTION_TAG_LIBELLE_1 = "Tag1";

    public static final String QUESTION_TAG_LIBELLE_2 = "Tag2";

    public static final String QUESTION_TAG_LIBELLE_3 = "Tag3";

    public static final String QUESTIONNAIRE_TAG_LIBELLE_1 = "Tag10";

    public static final String CATEGORIE_LIBELLE = "Categ lib";

    public static final String QUESTIONNAIRE_TITLE = "Questionnaire";

    public static final String QUESTIONNAIRE_DESC = "Questionnaire desc";

    public static final String USER = SpringBootJpaTestConfig.USER_TEST;
}
