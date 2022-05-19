package com.emu.apps.qcm.rest.controllers.management.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static com.emu.apps.qcm.rest.controllers.management.resources.Constants.ZONED_DATE_TIME_FORMAT;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "Webhook")
public class WebHookResource {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ZONED_DATE_TIME_FORMAT)
    private ZonedDateTime dateModification;

    @NotNull
    @JsonProperty("url")
    private String url;

    @JsonProperty("secret")
    private String secret;

    @JsonProperty("defaultTimeOut")
    private Long defaultTimeOut;

}
