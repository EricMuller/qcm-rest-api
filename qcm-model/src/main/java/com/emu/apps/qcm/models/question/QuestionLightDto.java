package com.emu.apps.qcm.models.question;


import com.emu.apps.qcm.models.DomainDto;
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
public class QuestionLightDto extends DomainDto {

    private String question;

    private String type;

    private String status;
}
