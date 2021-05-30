package com.emu.apps.qcm.domain.model.category;


import com.emu.apps.qcm.domain.model.base.DomainObjectId;
import lombok.NoArgsConstructor;

public class CategoryId extends DomainObjectId {
    public CategoryId(String uuid) {
        super(uuid);
    }
}
