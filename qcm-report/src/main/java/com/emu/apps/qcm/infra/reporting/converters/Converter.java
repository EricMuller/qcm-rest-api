package com.emu.apps.qcm.infra.reporting.converters;

import java.util.Map;

public interface Converter {
    default byte[] convert(Object context) {
        return new byte[0];
    }

    default byte[] convert(Map <String, Object> context, String template) {
        return convert(context);
    }

}
