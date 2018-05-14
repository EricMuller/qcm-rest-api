package com.emu.apps.qcm.services.repositories.specifications.questionnaire;

import com.emu.apps.qcm.services.entity.questionnaires.Questionnaire;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;
import com.emu.apps.qcm.services.repositories.specifications.BaseSpecification;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;
import java.util.Arrays;

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class QuestionnaireSpecification extends BaseSpecification<Questionnaire, FilterDto[]> {


    private Long[] getTagIds(FilterDto[] filterDtos) {
        return Arrays.stream(filterDtos).filter((filterDto -> "tag_id".equals(filterDto.getName())))
                .map((t) -> t.getId()).toArray(it -> new Long[it]);
    }

    private String getTitle(FilterDto[] filterDtos) {
        return null;
    }

    @Override
    public Specification<Questionnaire> getFilter(final FilterDto[] filters) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the questionnaireTags specifications
            return where(
                    where(questionnaireTitleContains(getTitle(filters)))
                            .and(tagIdIn(getTagIds(filters))))
                    .toPredicate(root, query, cb);
        };
    }

    private Specification<Questionnaire> questionnaireTitleContains(String title) {
        return questionnaireAttributeContains("title", title);
    }

    private Specification<Questionnaire> tagIdIn(Long[] ids) {
        return tagAttributeIn("id", ids);
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

    private Specification<Questionnaire> tagAttributeIn(String attribute, Long values[]) {
        return (root, query, cb) -> {
            if (ArrayUtils.isEmpty(values)) {
                return null;
            }
            SetJoin<Questionnaire, QuestionnaireTag> questionnaireTags = root.joinSet("questionnaireTags", JoinType.INNER);
            return questionnaireTags.join("tag").get("id").in(values);
        };
    }
}