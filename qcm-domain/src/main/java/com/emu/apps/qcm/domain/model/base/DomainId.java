package com.emu.apps.qcm.domain.model.base;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DomainId extends DomainObjectId {

    private Long version;

    private ZonedDateTime dateCreation;

    private ZonedDateTime dateModification;

}
