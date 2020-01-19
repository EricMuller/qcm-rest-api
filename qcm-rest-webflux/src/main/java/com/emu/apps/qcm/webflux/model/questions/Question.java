package com.emu.apps.qcm.webflux.model.questions;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @Id
    private Long id;


    private String mandatory;

    @SuppressWarnings("squid:S1700")

    private String question;

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Response> responses;

//    private Type type;
//
//    private Status status = Status.DRAFT;
//
//    private Set<QuestionTag> questionTags = new HashSet<>();
//
//    private Set<QuestionnaireQuestion> questionnaireQuestions = new HashSet<>();

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
