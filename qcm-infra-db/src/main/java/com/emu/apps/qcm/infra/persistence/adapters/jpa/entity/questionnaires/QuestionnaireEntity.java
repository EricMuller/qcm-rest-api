package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.infra.persistence.adapters.jpa.converters.BooleanTFConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.annotations.BatchSize;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.JoinType;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static org.springframework.data.jpa.domain.Specification.where;

@Entity
@NamedEntityGraph(name = "Questionnaire.questionnaireTags",
        attributeNodes = {
                @NamedAttributeNode(value = "tags", subgraph = "tags")
        },
        subgraphs = @NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag")))
@NamedEntityGraph(name = "Questionnaire.questionnaire",
        attributeNodes = {
                @NamedAttributeNode(value = "tags", subgraph = "tags"),
                @NamedAttributeNode(value = "questionnaireQuestions", subgraph = "questions"),
        },
        subgraphs = {@NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag")),
                @NamedSubgraph(name = "questions", attributeNodes = @NamedAttributeNode("question"))}
)
@Table(name = "QUESTIONNAIRE",
        indexes = {@Index(name = "IDX_QTE_CREATE_BY_IDX", columnList = "created_by"),
                @Index(name = "IDX_QTE_UUID_IDX", columnList = "uuid")})
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionnaire_generator")
    @SequenceGenerator(name = "questionnaire_generator", sequenceName = "questionnaire_seq", allocationSize = 1)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "DESCRIPTION",length = 2000)
    private String description;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LOCALE")
    private String locale;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "PUBLISHED")
    @Convert(converter = BooleanTFConverter.class)
    private Boolean published = Boolean.FALSE;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position asc")
    private Set <QuestionnaireQuestionEntity> questionnaireQuestions = new HashSet <>();

    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set <QuestionnaireTagEntity> tags = new HashSet <>();

    public QuestionnaireEntity(UUID uuid) {
        super(uuid);
    }

    public static final class SpecificationBuilder extends BaseSpecification <QuestionnaireEntity> {

        private static final String TITLE_FIELD = "title";

        private String title;

        private Boolean published;

        private String principal;

        private UUID[] tagUuids;


        @SuppressWarnings("squid:S1172")
        private SpecificationBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public SpecificationBuilder setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public SpecificationBuilder setPublished(Boolean published) {
            this.published = published;
            return this;
        }

        @Override
        public Specification <QuestionnaireEntity> build() {

            return (root, query, cb) -> {
                // Important because of the join in the questionnaireTags
                query.distinct(true);

                Specification<QuestionnaireEntity> where = where(fieldContains(TITLE_FIELD, title)
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
                            .joinSet("tags", JoinType.INNER)
                            .join("tag")
                            .get(UUID)
                            .in((Object[]) values);
        }

        public void setTagUuids(UUID[] tagUuids) {
            this.tagUuids = tagUuids;
        }
    }
}
