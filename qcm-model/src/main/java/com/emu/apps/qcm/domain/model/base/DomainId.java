package com.emu.apps.qcm.domain.model.base;

import com.emu.apps.qcm.domain.model.base.DomainObjectId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DomainId extends DomainObjectId {

    @JsonProperty("version")
    private Long version;

    @JsonProperty("dateCreation")
    private ZonedDateTime dateCreation;

    @JsonProperty("dateModification")
    private ZonedDateTime dateModification;

}
