package com.emu.apps.qcm.domain.model.category;


import com.emu.apps.qcm.domain.model.base.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by eric on 05/06/2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MpttCategory extends DomainId<CategoryId> {

    private String libelle;

    private String type;

    private String userId;



}
