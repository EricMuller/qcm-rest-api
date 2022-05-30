package com.emu.apps.shared.converters;

import java.util.Map;

public interface Converter {

    String PDF = "XdocPdfConverter";
    String WORD = "XdocWordConverter";

    byte[] convert(Map <String, Object> context, String template);

}
