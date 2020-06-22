package com.emu.apps.qcm.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileQuestionDto {

    private Long id;

    private Long number;

    private String question;

    private String response;

    private String categorie;


    @Override
    public String toString() {
        return String.format("Question[id=%d, question='%s', response='%s']", id, question, response);
    }
}
