package com.emu.apps.shared.converters;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

public class WaterMarkPdf {


	public static void watermarkPdf(String sourceFile, String destinationFile) throws IOException {

		File input = new File(sourceFile);
		File outPut = new File(destinationFile);

		watermarkPdf(new FileInputStream(input) , new FileOutputStream(outPut));

	}
	@SuppressWarnings("rawtypes")
	public static void watermarkPdf(InputStream inputStream, OutputStream outputStream) throws IOException {

		PDDocument pdf;
		pdf = PDDocument.load(inputStream);
		InputStream is = WaterMarkPdf.class.getClassLoader().getResourceAsStream("draft.png");

		BufferedImage buffered = ImageIO.read(is);
		PDJpeg watermark = new PDJpeg(pdf, buffered);

		List pages = pdf.getDocumentCatalog().getAllPages();
		Iterator iter = pages.iterator();

		while (iter.hasNext()) {
			PDPage page = (PDPage) iter.next();

			PDPageContentStream stream = new PDPageContentStream(pdf, page, true, false);
			stream.drawImage(watermark, 100, 400);
			stream.close();
		}

		try {
			pdf.save(outputStream);
			pdf.close();
		} catch (COSVisitorException e) {
			e.printStackTrace();
		}
	}
}
