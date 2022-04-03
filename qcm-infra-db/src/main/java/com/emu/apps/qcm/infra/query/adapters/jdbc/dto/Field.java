package com.emu.apps.qcm.infra.query.adapters.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Field {

    private String name;
    private Object value;

}
