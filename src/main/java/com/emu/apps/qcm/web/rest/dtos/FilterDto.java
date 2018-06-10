package com.emu.apps.qcm.web.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class FilterDto {

    @ApiModelProperty(notes = "Filter name ")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Filter value")
    @JsonProperty("value")
    private String value;

    public FilterDto() {
    }

    public FilterDto(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String id) {
        this.value = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
