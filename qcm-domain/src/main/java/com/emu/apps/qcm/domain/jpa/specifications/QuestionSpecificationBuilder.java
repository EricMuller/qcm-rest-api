package com.emu.apps.qcm.domain.jpa.specifications;

import com.emu.apps.qcm.domain.entity.questions.Question;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


import javax.persistence.criteria.JoinType;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public final class QuestionSpecificationBuilder extends BaseSpecification<Question> {

    private Long[] tagIds;

    private Long[] questionnaireIds;

    private String principal;

    public QuestionSpecificationBuilder() {
        // nope
    }

    public QuestionSpecificationBuilder setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    public QuestionSpecificationBuilder setQuestionnaireIds(Long[] questionnaireIds) {
        this.questionnaireIds = questionnaireIds;
        return this;
    }

    public QuestionSpecificationBuilder setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }

    @Override
    public Specification<Question> build() {

        Specification where = fieldEquals(CREATED_BY, principal)
                .and(questionnaireTagsIdIn(tagIds))
                .and(questionnaireQuestionsIdIn(questionnaireIds));

        return (root, query, cb) -> {
            query.distinct(true);
            return where(where).toPredicate(root, query, cb);
        };
    }

    private Specification<Question> questionnaireTagsIdIn(@Nullable Long[] tagIds) {
        return ArrayUtils.isEmpty(tagIds) ? null :
                (root, query, cb) -> root.joinSet("questionTags", JoinType.INNER).join("tag").get(ID).in(tagIds);
    }

    private Specification<Question> questionnaireQuestionsIdIn(Long[] questionnaireIds) {
        return ArrayUtils.isEmpty(questionnaireIds) ? null :
                (root, query, cb) -> root.joinSet("questionnaireQuestions", JoinType.INNER)
                        .get(ID).get("questionnaireId").in(questionnaireIds);
    }

}
