package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questions;


import com.emu.apps.qcm.domain.model.question.TypeQuestion;
import com.emu.apps.qcm.domain.model.user.Account;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.AccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableAccountEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.converters.BooleanTFConverter;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires.QuestionnaireQuestionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.fromString;
import static org.springframework.data.jpa.domain.Specification.where;

/**
 * Created by eric on 05/06/2017.
 */

@Entity
@NamedEntityGraph(name = "Question.questionTags",
        attributeNodes = {
                @NamedAttributeNode(value = "tags", subgraph = "tags")
        },
        subgraphs = @NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag")))
@Table(name = "QUESTION",
        indexes = {@Index(name = "IDX_QTO_CREATE_BY_IDX", columnList = "created_by"),
                @Index(name = "IDX_QTO_UUID_IDX", columnList = "uuid")
        }
        , uniqueConstraints = {@UniqueConstraint(name = "UK_QTO_UUID", columnNames  = {"uuid"})}
        )
@Getter
@Setter
@NoArgsConstructor
public class QuestionEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name = "question_generator", sequenceName = "question_seq", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Convert(converter = BooleanTFConverter.class)
    @Column(name = "MANDATORY")
    private Boolean mandatory;

    @SuppressWarnings("squid:S1700")
    @Column(name = "QUESTION", nullable = false, length = 1024)
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private TypeQuestion type;

    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;

//    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private String status ;

    @Column(name = "TIP", length = 1024)
    private String tip;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_ID")
    @BatchSize(size = 100)
    private List <ResponseEntity> responses;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    private Set <QuestionTagEntity> tags = new HashSet <>();

    // used in QuestionSpecificationBuilder !
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set <QuestionnaireQuestionEntity> questionnaireQuestions = new HashSet <>();

    @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "CREATED_BY", updatable = false, nullable = false)
    protected AccountEntity account;


    public QuestionEntity(UUID uuid) {
        super(uuid);
    }

    @Override
    public String toString() {
        return String.format("Question[id=%d, question='%s']", getId(), questionText);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    public static final class SpecificationBuilder extends BaseSpecification <QuestionEntity> {

        private final UUID accountUuid;

        private UUID[] tagUuids;

        private UUID[] questionnaireUuids;

        private String principal;

        public SpecificationBuilder(String accountUuid) {
            this.accountUuid = fromString(accountUuid);
        }

        public SpecificationBuilder setTagUuids(UUID[] tagUuids) {
            this.tagUuids = tagUuids;
            return this;
        }

        public SpecificationBuilder setQuestionnaireUuids(UUID[] questionnaireUuids) {
            this.questionnaireUuids = questionnaireUuids;
            return this;
        }

        @Override
        public Specification <QuestionEntity> build() {

//            Specification<QuestionEntity> where = fieldEquals(CREATED_BY, principal)
//                    .and(questionnaireQuestionsUuidIn(questionnaireUuids))
//                    .and(questionnaireTagsUuidIn(tagUuids));

            Specification<QuestionEntity> where =
                    accountUuidIn(accountUuid)
                    .and(questionnaireQuestionsUuidIn(questionnaireUuids))
                    .and(questionnaireTagsUuidIn(tagUuids));

            return (root, query, cb) -> {
                query.distinct(true);
                return where(where).toPredicate(root, query, cb);
            };
        }

        private Specification<QuestionEntity> accountUuidIn(@Nullable UUID uuid) {
            return Objects.isNull(uuid) ? null :
                    (root, query, cb) -> root.join("account", JoinType.INNER)
                            .get(ID).in(uuid);
        }
        private Specification<QuestionEntity> questionnaireTagsUuidIn(@Nullable UUID[] tagUuids) {
            return ArrayUtils.isEmpty(tagUuids) ? null :
                    (root, query, cb) -> root.joinSet("tags", JoinType.INNER)
                            .join("tag").get(UUID)
                            .in((Object[]) tagUuids);
        }

        private Specification<QuestionEntity> questionnaireQuestionsUuidIn(UUID[] questionnaireUuids) {
            return ArrayUtils.isEmpty(questionnaireUuids) ? null :
                    (root, query, cb) -> root.joinSet("questionnaireQuestions", JoinType.INNER)
                            .join("questionnaire").get(UUID)
                            .in((Object[]) questionnaireUuids);
        }

    }

}
