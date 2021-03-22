package com.emu.apps.qcm.domain.models.question;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String uuid;

    @JsonProperty("response")
    private String responseText;

    private Boolean good;

    private Long version;

    @JsonProperty(value = "number")
    private Long number;

}
