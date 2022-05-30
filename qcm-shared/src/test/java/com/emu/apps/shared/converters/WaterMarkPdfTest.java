package com.emu.apps.shared.converters;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WaterMarkPdfTest {

    @Test
    void watermarkPdfTest() throws IOException {


        File  file = new File("Java-Interview-questions-VALIDATED-watermaked.pdf");

        FileOutputStream fos = new FileOutputStream(file);

        WaterMarkPdf.watermarkPdf(WaterMarkPdfTest.class.getResourceAsStream("/Java-Interview-questions-VALIDATED-2.pdf"),
                fos);

    }
}
