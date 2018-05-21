package com.emu.apps.qcm.services.repositories.specifications.question;

import com.emu.apps.qcm.services.entity.questions.Question;
import com.emu.apps.qcm.services.entity.tags.QuestionTag;
import com.emu.apps.qcm.services.repositories.specifications.BaseSpecification;
import com.emu.apps.qcm.web.rest.dtos.FilterDto;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.SetJoin;
import java.security.Principal;
import java.util.Arrays;

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class QuestionSpecification extends BaseSpecification<Question, FilterDto[]> {


    private Long[] getTagIds(FilterDto[] filterDtos) {
        return Arrays.stream(filterDtos).filter((filterDto -> "tag_id".equals(filterDto.getName())))
                .map((t) -> t.getId()).toArray(it -> new Long[it]);
    }

    private String getTitle(FilterDto[] filterDtos) {
        return null;
    }

    @Override
    public Specification<Question> getFilter(final FilterDto[] filters, Principal principal) {
        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the questionnaireTags specifications
            return
                    where(questionTitleContains(getTitle(filters)))
                            .and(tagIdIn(getTagIds(filters)))
                            .and(questionCreatdByEqual(principal.getName())
                            )
                            .toPredicate(root, query, cb);
        };
    }

    private Specification<Question> questionTitleContains(String title) {
        return questionAttributeContains("title", title);
    }

    private Specification<Question> tagIdIn(Long[] ids) {
        return tagAttributeIn("id", ids);
    }

    private Specification<Question> questionCreatdByEqual(String name) {
        return questionAttributeEquals("createdBy", name);
    }

    private Specification<Question> questionAttributeContains(String attribute, String value) {
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

    private Specification<Question> questionAttributeEquals(String attribute, String value) {
        return (root, query, cb) -> {
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return cb.equal(root.get(attribute), value);
        };
    }

    private Specification<Question> tagAttributeIn(String attribute, Long values[]) {
        return (root, query, cb) -> {
            if (ArrayUtils.isEmpty(values)) {
                return null;
            }
            SetJoin<Question, QuestionTag> questionnaireTags = root.joinSet("questionTags", JoinType.INNER);
            return questionnaireTags.join("tag").get("id").in(values);
        };
    }
}