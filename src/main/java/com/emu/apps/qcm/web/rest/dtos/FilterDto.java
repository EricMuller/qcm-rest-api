package com.emu.apps.qcm.web.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {

    @ApiModelProperty(notes = "Filter name ")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Filter value")
    @JsonProperty("value")
    private String value;


}
