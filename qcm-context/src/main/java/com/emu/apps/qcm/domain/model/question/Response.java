package com.emu.apps.qcm.domain.model.question;

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
public class Response {

    private String uuid;

    private String responseText;

    private Boolean good;

    private Long number;

}
