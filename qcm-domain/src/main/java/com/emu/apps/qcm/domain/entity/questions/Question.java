package com.emu.apps.qcm.domain.entity.questions;


import com.emu.apps.qcm.domain.entity.Status;
import com.emu.apps.qcm.domain.entity.category.Category;
import com.emu.apps.qcm.domain.entity.common.AuditableEntity;
import com.emu.apps.qcm.domain.entity.converters.BooleanTFConverter;
import com.emu.apps.qcm.domain.entity.questionnaires.QuestionnaireQuestion;
import com.emu.apps.qcm.domain.entity.tags.QuestionTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eric on 05/06/2017.
 */

@Entity
@NamedEntityGraph(name = "Question.questionTags",
        attributeNodes = {
                @NamedAttributeNode(value = "questionTags", subgraph = "tags")
        },
        subgraphs = @NamedSubgraph(name = "tags", attributeNodes = @NamedAttributeNode("tag")))
@Table(indexes = {@Index(name = "IDX_QTO_CREATE_BY_IDX", columnList = "created_by")})
@Getter
@Setter
@NoArgsConstructor
public class Question extends AuditableEntity <String> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_generator")
    @SequenceGenerator(name = "question_generator", sequenceName = "question_seq")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Convert(converter = BooleanTFConverter.class)
    private Boolean mandatory;

    @SuppressWarnings("squid:S1700")
    @Column(name = "question", nullable = false, length = 1024)
    private String question;

    @OneToMany(cascade = CascadeType.ALL)
    private List <Response> responses;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DRAFT;

    @Column(name = "tip", length = 1024)
    private String tip;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    private Set <QuestionTag> questionTags = new HashSet <>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set <QuestionnaireQuestion> questionnaireQuestions = new HashSet <>();

    @Override
    public String toString() {
        return String.format("Question[id=%d, question='%s']", getId(), question);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
