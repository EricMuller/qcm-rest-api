package com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.questionnaires;

import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.Status;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.category.CategoryEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.converters.BooleanTFConverter;
import com.emu.apps.qcm.spi.persistence.adapters.jpa.entity.tags.QuestionnaireTagEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraph(name = "Questionnaire.questionnaireTags",
        attributeNodes = {
                @NamedAttributeNode(value = "questionnaireTags", subgraph = "tags")
        },
        subgraphs = @NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag")))
@NamedEntityGraph(name = "Questionnaire.questionnaire",
        attributeNodes = {
                @NamedAttributeNode(value = "questionnaireTags", subgraph = "tags"),
                @NamedAttributeNode(value = "questionnaireQuestions", subgraph = "questions"),
        },
        subgraphs = {@NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag")),
                @NamedSubgraph(name = "questions", attributeNodes = @NamedAttributeNode("question"))}
)
@Table(name = "questionnaire",
        indexes = {@Index(name = "IDX_QTE_CREATE_BY_IDX", columnList = "created_by"),
                @Index(name = "IDX_QTE_UUID_IDX", columnList = "uuid")})
@Getter
@Setter
@NoArgsConstructor
public class QuestionnaireEntity extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionnaire_generator")
    @SequenceGenerator(name = "questionnaire_generator", sequenceName = "questionnaire_seq", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LOCALE")
    private String locale;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "PUBLISHED")
    @Convert(converter = BooleanTFConverter.class)
    private Boolean published = Boolean.FALSE;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set <QuestionnaireQuestionEntity> questionnaireQuestions = new HashSet <>();

    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set <QuestionnaireTagEntity> questionnaireTags = new HashSet <>();

}
