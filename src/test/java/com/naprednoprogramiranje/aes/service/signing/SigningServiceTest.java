package com.naprednoprogramiranje.aes.service.signing;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.signatures.PdfSignature;
import com.itextpdf.signatures.SignatureUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SigningServiceTest {

    SigningService signingService;
    private final String signatureFieldName = "Advanced Electronic Signature";
    private final String certSubjectName = "CN=www.aes.com,OU=Zastita racunarskih sistema,O=Fakultet organizacionih nauka,L=Belgrade,ST=Serbia,C=RS";
    private final String reason = "Test signing";
    private final String location = "Belgrade";

    @BeforeEach
    public void setUp() {
        signingService = new SigningService();
    }

    @AfterEach
    public void tearDown() {
        signingService = null;
    }

    @Test
    public void testSign() throws Exception {
        Path pdfPath = Paths.get("src/test/resources/testPdf.pdf");
        // sign pdf
        String outPdfPath = signingService.sign(pdfPath, reason, location);

        // extract signature details
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(outPdfPath));
        SignatureUtil util = new SignatureUtil(pdfDoc);
        // has target signature field name
        assertTrue(util.doesSignatureFieldExist(signatureFieldName), "Signature field doesn't exist");
        // test fields
        PdfSignature sig = util.getSignature(signatureFieldName);
        assertEquals(sig.getReason(), reason, "Reason in the signature is not valid");
        assertEquals(sig.getLocation(), location, "Location in the signature is not valid");
        // test certificate details
        String certSubject = util.readSignatureData(signatureFieldName).getSigningCertificate().getSubjectX500Principal().getName();
        assertEquals(certSubject, certSubjectName, "Certificate subject doesn't match");

        //close file
        pdfDoc.close();
    }

}
