package com.jacky.webtools.ctrl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.PdfMerger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;

@Tag(name = "PDF")
@RestController
@RequestMapping("pdf")
public class PdfController {

    @Operation(summary = "合并")
    @PostMapping(value = "merge", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void merge(@RequestPart("file1") MultipartFile file1, @RequestPart("file2") MultipartFile file2, HttpServletResponse response) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument mergedPdf = new PdfDocument(writer);
            PdfMerger merger = new PdfMerger(mergedPdf);

            for (InputStream inputFile : Arrays.asList(file1.getInputStream(), file2.getInputStream())) {
                PdfDocument pdfDoc = new PdfDocument(new PdfReader(inputFile));
                merger.merge(pdfDoc, 1, pdfDoc.getNumberOfPages());
                pdfDoc.close();
            }

            mergedPdf.close();

            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=\"merged.pdf\"");
            response.getOutputStream().write(baos.toByteArray());
            response.flushBuffer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
