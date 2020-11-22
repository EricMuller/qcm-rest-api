package com.emu.apps.qcm.aggregates.question;


import com.emu.apps.qcm.aggregates.Domain;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
public class QuestionLight extends Domain {

    @JsonProperty("question")
    private String questionText;

    private String type;

    private String status;
}
