package com.emu.apps.qcm.rest.controllers.resources.published;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonRootName(value = "PublishedResponse")
public class PublishedResponse {

    private String responseText;

    private String status;

    private Boolean good;

    private Long version;

    private Long number;

}
