package com.emu.apps.qcm.infra.query.adapters.jdbc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Results {
    private String id;

    private List <Row> datas;
}
