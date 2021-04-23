package com.emu.apps.qcm.domain.model.export.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
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
@JsonRootName(value = "ResponseExport")
public class ResponseExport {

    @JsonProperty("response")
    private String responseText;

    private Boolean good;

    private Long version;

    private Long number;
}
