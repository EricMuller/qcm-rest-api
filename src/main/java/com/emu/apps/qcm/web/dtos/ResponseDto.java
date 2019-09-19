package com.emu.apps.qcm.web.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private Long id;

    private String response;

    private Boolean good;

    private Long version;


}
