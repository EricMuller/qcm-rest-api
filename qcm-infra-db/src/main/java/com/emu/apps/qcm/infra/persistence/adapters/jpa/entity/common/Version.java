package com.emu.apps.qcm.infra.persistence.adapters.jpa.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Version implements Serializable {

    @Column(name = "NUM_VERSION", nullable = false)
    private int numero = 0 ;

    @Column(name = "VALIDATION_DATE")
    private ZonedDateTime dateValidation;

}
