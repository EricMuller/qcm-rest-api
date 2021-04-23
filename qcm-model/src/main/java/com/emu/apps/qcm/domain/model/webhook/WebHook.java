package com.emu.apps.qcm.domain.model.webhook;

import com.emu.apps.qcm.domain.model.base.DomainId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebHook extends DomainId {

    @NotNull
    @JsonProperty("url")
    private String url;

    @JsonProperty("secret")
    private String secret;

    @JsonProperty("defaultTimeOut")
    private Long defaultTimeOut;

}
