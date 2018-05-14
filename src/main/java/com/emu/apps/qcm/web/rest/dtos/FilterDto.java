package com.emu.apps.qcm.web.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class FilterDto {

    @ApiModelProperty(notes = "Filter name ")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(notes = "Filter value")
    @JsonProperty("value")
    private Long value;

    public FilterDto() {
    }

    public FilterDto(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return value;
    }

    public void setId(Long id) {
        this.value = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
