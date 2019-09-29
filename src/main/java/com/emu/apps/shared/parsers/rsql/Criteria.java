package com.emu.apps.shared.parsers.rsql;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Criteria {

//    @ApiModelProperty(notes = "Criteria name")
    @JsonProperty("name")
    private String name;

//    @ApiModelProperty(notes = "Criteria Operation")
    @JsonProperty("operation")
    private Operator operation;

//    @ApiModelProperty(notes = "Criteria value")
    @JsonProperty("value")
    private String value;

}
