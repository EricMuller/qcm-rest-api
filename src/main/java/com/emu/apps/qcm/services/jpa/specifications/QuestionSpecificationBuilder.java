package com.emu.apps.qcm.services.jpa.specifications;

import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
import com.emu.apps.shared.parsers.rsql.Criteria;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public final class QuestionSpecificationBuilder extends BaseSpecification<Question, Criteria[]> {

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

        return (Specification<Question>) (root, query, cb) -> {
            query.distinct(true);
            return where(where).toPredicate(root, query, cb);
        };
    }

    private Specification<Question> questionnaireTagsIdIn(Long[] tagIds) {
        return ArrayUtils.isEmpty(tagIds) ? null :
                (root, query, cb) -> {
                    SetJoin<Question, QuestionTag> questionnaireTags = root.joinSet("questionTags", JoinType.INNER);
                    return questionnaireTags.join("tag").get(ID).in(tagIds);
                };
    }

    private Specification<Question> questionnaireQuestionsIdIn(Long[] questionnaireIds) {
        return ArrayUtils.isEmpty(questionnaireIds) ? null :
                (root, query, cb) -> root.joinSet("questionnaireQuestions", JoinType.INNER)
                        .get(ID).get("questionnaireId").in(questionnaireIds);
    }

}
