package com.emu.apps.qcm.infra.query.adapters.jdbc.dto;


import com.emu.apps.qcm.infra.query.adapters.jdbc.dto.Field;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Row {

    private Field[] fields;
    public Row(int columnCount) {
        this.fields = new Field[columnCount];
    }



}
