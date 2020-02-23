package com.emu.apps.qcm.services.entity.questionnaires;

import com.emu.apps.qcm.services.entity.Status;
import com.emu.apps.qcm.services.entity.category.QuestionCategory;
import com.emu.apps.qcm.services.entity.category.QuestionnaireCategory;
import com.emu.apps.qcm.services.entity.common.AuditableEntity;
import com.emu.apps.qcm.services.entity.tags.QuestionnaireTag;
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
@Table(indexes = {@Index(name = "IDX_QTE_CREATE_BY_IDX", columnList = "created_by")})
@Getter
@Setter
@NoArgsConstructor
public class Questionnaire extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "questionnaire_generator")
    @SequenceGenerator(name = "questionnaire_generator", sequenceName = "questionnaire_seq", allocationSize = 50)
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

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set <QuestionnaireQuestion> questionnaireQuestions = new HashSet <>();

    @ManyToOne(fetch = FetchType.EAGER)
    private QuestionnaireCategory category;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set <QuestionnaireTag> questionnaireTags = new HashSet <>();

    public Questionnaire(String title) {
        this.title = title;
    }


}
