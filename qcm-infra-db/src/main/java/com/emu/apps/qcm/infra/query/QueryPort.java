package com.emu.apps.qcm.infra.query;

import com.emu.apps.qcm.infra.query.adapters.jdbc.dto.Results;


public interface QueryPort {
    Results executeQuery(String name, String sql);
}
