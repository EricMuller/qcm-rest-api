package com.emu.apps.qcm.spi.persistence.adapters.jpa.specifications;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires.QuestionnaireEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public final class QuestionnaireSpecificationBuilder extends BaseSpecification <QuestionnaireEntity> {

    private static final String TITLE_FIELD = "title";

    private String title;

    private Boolean published;

    private String principal;

    private UUID[] tagUuids;

    public QuestionnaireSpecificationBuilder() {
//nope
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

    public QuestionnaireSpecificationBuilder setPublished(Boolean published) {
        this.published = published;
        return this;
    }

    @Override
    public Specification <QuestionnaireEntity> build() {

        return (root, query, cb) -> {
            // Important because of the join in the questionnaireTags
            query.distinct(true);

            Specification where = where(fieldContains(TITLE_FIELD, title)
                    .and(questionnaireTagsUuidIn(tagUuids))
            ); //

            if (Objects.nonNull(principal)) {
                where = where.and(fieldEquals(CREATED_BY, principal));
            }

            if (Objects.nonNull(published)) {
                where = where.and(fieldEquals(PUBLISHED, published));
            }

            return where(where).toPredicate(root, query, cb);
        };
    }

    private Specification <QuestionnaireEntity> questionnaireTagsUuidIn(UUID[] values) {
        return ArrayUtils.isEmpty(values) ? null :
                (root, query, cb) -> root
                        .joinSet("questionnaireTags", JoinType.INNER)
                        .join("tag")
                        .get(UUID)
                        .in(values);
    }

    public void setTagUuids(UUID[] tagUuids) {
        this.tagUuids = tagUuids;
    }
}
