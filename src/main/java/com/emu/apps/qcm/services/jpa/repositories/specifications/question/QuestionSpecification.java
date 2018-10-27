package com.emu.apps.qcm.services.jpa.repositories.specifications.question;

import com.emu.apps.qcm.services.jpa.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.services.jpa.entity.questions.Question;
import com.emu.apps.qcm.services.jpa.entity.tags.QuestionTag;
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

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class QuestionSpecification extends BaseSpecification<Question, FilterDto[]> {

    private static final String TAG_ID = "tag_id";
    private static final String QUESTIONNAIRE_ID = "questionnaire_id";
    private static final String TITLE = "title";
    private static final String CREATED_BY = "createdBy";
    private static final String ID = "id";

    private Long[] getTagIds(FilterDto[] filterDtos) {
        return Arrays.stream(filterDtos).filter((filterDto -> TAG_ID.equals(filterDto.getName())))
                .map((t) -> Long.valueOf(t.getValue())).toArray(it -> new Long[it]);
    }

    private Long[] getQuestionnairesIds(FilterDto[] filterDtos) {
        return Arrays.stream(filterDtos).filter((filterDto -> QUESTIONNAIRE_ID.equals(filterDto.getName())))
                .map((t) -> Long.valueOf(t.getValue())).toArray(it -> new Long[it]);
    }

    private String getTitle(FilterDto[] filterDtos) {
        return null;
    }

    @Override
    public Specification<Question> getSpecifications(final FilterDto[] filters, Principal principal) {

        return (root, query, cb) -> {
            query.distinct(true); //Important because of the join in the questionnaireTags specifications
            return
                    where(tagIdIn(getTagIds(filters)))
                            .and(questionCreatdByEqual(principal.getName()))
                            .and(questionnaireIdIn(getQuestionnairesIds(filters))
                            )
                            .toPredicate(root, query, cb);
        };
    }

    private Specification<Question> questionTitleContains(String title) {
        return questionAttributeContains(TITLE, title);
    }


    private Specification<Question> tagIdIn(Long[] ids) {
        return tagAttributeIn(ID, ids);
    }

    private Specification<Question> questionnaireIdIn(Long[] ids) {
        return questionnaireAttributeIn(ids);
    }

    private Specification<Question> questionCreatdByEqual(String name) {
        return questionAttributeEquals(CREATED_BY, name);
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
            return questionnaireTags.join("tag").get(attribute).in(values);
        };
    }

    private Specification<Question> questionnaireAttributeIn( Long values[]) {
        return (root, query, cb) -> {
            if (ArrayUtils.isEmpty(values)) {
                return null;
            }
            SetJoin<Question, QuestionnaireQuestion> questionnaireQuestions = root.joinSet("questionnaireQuestions", JoinType.INNER);
            return questionnaireQuestions.get(ID).get("questionnaireId").in(values);
        };
    }
}