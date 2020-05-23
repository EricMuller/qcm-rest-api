package com.emu.apps.qcm.web.dtos.question;


import com.emu.apps.qcm.web.dtos.EntityDto;
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
public class QuestionLightDto extends EntityDto {

    private String question;

    private String type;

    private String status;
}
