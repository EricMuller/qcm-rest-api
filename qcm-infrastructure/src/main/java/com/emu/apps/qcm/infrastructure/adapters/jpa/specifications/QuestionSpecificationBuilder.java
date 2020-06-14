package com.emu.apps.qcm.infrastructure.adapters.jpa.specifications;

import com.emu.apps.qcm.infrastructure.adapters.jpa.entity.questions.Question;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


import javax.persistence.criteria.JoinType;

import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public final class QuestionSpecificationBuilder extends BaseSpecification<Question> {

    private Long[] tagIds;

    private UUID[] tagUuids;

    private UUID[] questionnaireUuids;

    private String principal;

    public QuestionSpecificationBuilder() {
        // nope
    }

    public QuestionSpecificationBuilder setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
        return this;
    }



    public QuestionSpecificationBuilder setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }

    public QuestionSpecificationBuilder setTagUuids(UUID[] tagUuids) {
        this.tagUuids = tagUuids;
        return this;
    }

    public QuestionSpecificationBuilder setQuestionnaireUuids(UUID[] questionnaireUuids) {
        this.questionnaireUuids = questionnaireUuids;
        return this;
    }

    @Override
    public Specification<Question> build() {

        Specification where = fieldEquals(CREATED_BY, principal)
                .and(questionnaireQuestionsUuidIn(questionnaireUuids))
                .and(questionnaireTagsUuidIn(tagUuids));

        return (root, query, cb) -> {
            query.distinct(true);
            return where(where).toPredicate(root, query, cb);
        };
    }

    private Specification<Question> questionnaireTagsUuidIn(@Nullable UUID[] tagUuids) {
        return ArrayUtils.isEmpty(tagUuids) ? null :
                (root, query, cb) -> root.joinSet("questionTags", JoinType.INNER)
                        .join("tag").get(UUID)
                        .in(tagUuids);
    }

    private Specification<Question> questionnaireQuestionsUuidIn(UUID[] questionnaireUuids) {
        return ArrayUtils.isEmpty(questionnaireUuids) ? null :
                (root, query, cb) -> root.joinSet("questionnaireQuestions", JoinType.INNER)
                        .join("questionnaire").get(UUID)
                        .in(questionnaireUuids);
    }

}
