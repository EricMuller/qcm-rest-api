package com.emu.apps.qcm.webmvc.services.jpa.entity.questionnaires;

import com.emu.apps.qcm.webmvc.services.jpa.entity.Status;
import com.emu.apps.qcm.webmvc.services.jpa.entity.category.Category;
import com.emu.apps.qcm.webmvc.services.jpa.entity.common.AuditableEntity;
import com.emu.apps.qcm.webmvc.services.jpa.entity.tags.QuestionnaireTag;
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
public class Questionnaire extends AuditableEntity<String> {

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

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuestionnaireQuestion> questionnaireQuestions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set<QuestionnaireTag> questionnaireTags = new HashSet<>();

    public Questionnaire(String title) {
        this.title = title;
    }


}
