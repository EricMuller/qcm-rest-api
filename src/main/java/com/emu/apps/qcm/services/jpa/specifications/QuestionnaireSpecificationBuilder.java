package com.emu.apps.qcm.services.jpa.specifications;

import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public final class QuestionnaireSpecificationBuilder extends BaseSpecification<Questionnaire> {

    private static final String TITLE_FIELD = "title";

    private String title;

    private String principal;

    private Long[] tagIds;

    public QuestionnaireSpecificationBuilder() {
        // nope
    }

    @SuppressWarnings("squid:S1172")
    private QuestionnaireSpecificationBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public QuestionnaireSpecificationBuilder setPrincipal(String principal) {
        this.principal = principal;
        return this;
    }

    public QuestionnaireSpecificationBuilder setTagIds(Long[] tagIds) {
        this.tagIds = tagIds;
        return this;
    }


    @Override
    public Specification<Questionnaire> build() {

        Specification where = where(fieldContains(TITLE_FIELD, title)
                .and(fieldEquals(CREATED_BY, principal))
                .and(questionnaireTagsIdIn(tagIds))); //

        return (root, query, cb) -> {
            // Important because of the join in the questionnaireTags
            query.distinct(true);
            return where(where).toPredicate(root, query, cb);
        };
    }

    private Specification<Questionnaire> questionnaireTagsIdIn(Long[] values) {
        return ArrayUtils.isEmpty(values) ? null :
                (root, query, cb) -> root.joinSet("questionnaireTags", JoinType.INNER).join("tag").get(ID).in(values);
    }
}
