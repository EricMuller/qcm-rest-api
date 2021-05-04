package com.emu.apps.qcm.domain.model.imports;

import com.emu.apps.qcm.domain.model.base.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Import extends DomainId {

    private String status;

    private byte[] data;

    private String libelle;

    private String type;

}
