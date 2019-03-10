package com.emu.apps.qcm.web.rest.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 14/06/2017.
 */
@ApiModel(value = "Message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    String message;


}
