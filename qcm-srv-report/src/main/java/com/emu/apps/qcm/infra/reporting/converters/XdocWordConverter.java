package com.emu.apps.qcm.infra.reporting.converters;

import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 *
 */
@Service(value = "XdocWordConverter")
@Slf4j
public class XdocWordConverter implements Converter {

    @Override
    public byte[] convert(Map <String, Object> map, String template) {

        try {
            try (InputStream in = XdocWordConverter.class.getResourceAsStream("/" + template)) {

                IXDocReport report = XDocReportRegistry
                        .getRegistry()
                        .loadReport(in, TemplateEngineKind.Velocity);
                IContext context = report.createContext(map);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                report.process(context, out);
                return out.toByteArray();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }


}
