package com.emu.apps.qcm.services.jpa.repositories.specifications.questionnaire;

import com.emu.apps.qcm.services.jpa.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.jpa.repositories.specifications.BaseSpecification;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;
import java.security.Principal;
import java.util.Arrays;

import static org.springframework.data.jpa.domain.Specification.where;


@Component
public class QuestionnaireSpecification extends BaseSpecification<Questionnaire, FilterDto[]> {


    private static final String TAG_ID = "tag_id";

    private static final String TITLE = "title";

    private static final String CREATED_BY = "createdBy";

    private static final String ID = "id";


    private Long[] getTagIds(FilterDto[] filterDtos) {
        return Arrays.stream(filterDtos).filter((filterDto -> TAG_ID.equals(filterDto.getName())))
                .map(t -> Long.valueOf(t.getValue())).toArray(it -> new Long[it]);
    }

    private String getTitle(FilterDto[] filterDtos) {
        return null;
    }

    @Override
    public Specification<Questionnaire> getSpecifications(final FilterDto[] filters, Principal principal) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the questionnaireTags specifications
            return
                    where(questionnaireTitleContains(getTitle(filters)))
                            .and(tagIdIn(getTagIds(filters)))
                            .and(questionnaireCreatdByEqual(principal.getName())
                            )
                            .toPredicate(root, query, cb);
        };
    }

    private Specification<Questionnaire> questionnaireTitleContains(String title) {
        return questionnaireAttributeContains(TITLE, title);
    }

    private Specification<Questionnaire> tagIdIn(Long[] ids) {
        return tagAttributeIn(ids);
    }

    private Specification<Questionnaire> questionnaireCreatdByEqual(String name) {
        return questionnaireAttributeEquals(CREATED_BY, name);
    }

    private Specification<Questionnaire> questionnaireAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return cb.like(
                    cb.lower(root.get(attribute)),
                    containsLowerCase(value)
            );
        };
    }

    private Specification<Questionnaire> questionnaireAttributeEquals(String attribute, String value) {
        return (root, query, cb) -> {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }

    private Specification<Questionnaire> tagAttributeIn(Long[] values) {
        return (root, query, cb) -> {
            if (ArrayUtils.isEmpty(values)) {
                return null;
            }
            SetJoin<Questionnaire, QuestionnaireTag> questionnaireTags = root.joinSet("questionnaireTags", JoinType.INNER);
            return questionnaireTags.join("tag").get(ID).in(values);
        };
    }
}
