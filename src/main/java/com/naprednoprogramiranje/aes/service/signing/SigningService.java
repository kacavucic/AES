package com.naprednoprogramiranje.aes.service.signing;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.signatures.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.UUID;

/**
 * Service used for electronic signing of the PDF document
 *
 * @author Katarina Vucic
 * @version 1.0
 */
public class SigningService {
    /**
     * First part of final destination path where signed pfd will be saved
     */
    final String DEST = "src/main/resources/static/";
    /**
     * Path to location of private keys used for signature
     */
    final String KEYSTORE = "src/main/resources/encryption/keystore.jks";
    /**
     * Access password for private keys
     */
    final char[] PASSWORD = "katarina".toCharArray();

    /**
     * Signs the document with predefined certificate
     *
     * @param src Path of the document to be signed
     * @param reason Reason of the signing. Will be stored in the electronic signature
     * @param location Location of the signing. Will be stored in the electronic signature
     *
     * @return Destination path of the signed document as a string
     *
     * @throws GeneralSecurityException If exception is thrown wile using KeyStore
     * @throws IOException If exception happens while reading from or writing to files
     */
    public String sign(Path src, String reason, String location)
            throws GeneralSecurityException, IOException {

        ///////////////////////////////////////////////////
        File file = new File(DEST);
        file.mkdir();
        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(new FileInputStream(KEYSTORE), PASSWORD);
        String alias = ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
        Certificate[] chain = ks.getCertificateChain(alias);
        ///////////////////////////////////////////////////

        String finalDest = DEST + UUID.randomUUID() + "_" + src.getFileName().toString();

        PdfReader reader = new PdfReader(src.toString());
        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(finalDest), new StampingProperties());
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(src.toString()));

        PdfSignatureAppearance appearance = signer.getSignatureAppearance();
        appearance.setPageRect(new Rectangle(293, 16, 303, 101));
        appearance.setPageNumber(pdfDocument.getNumberOfPages());
        appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.DESCRIPTION);
        appearance.setReason(reason);
        appearance.setLocation(location);

        PdfFormXObject layer0 = appearance.getLayer0();
        Rectangle rectangle = layer0.getBBox().toRectangle();
        PdfCanvas canvas = new PdfCanvas(layer0, signer.getDocument());
        canvas.setStrokeColor(new DeviceRgb(225, 234, 247)).setLineWidth(2);
        for (int i = (int) (rectangle.getLeft() - rectangle.getHeight()); i < rectangle.getRight(); i += 5)
            canvas.moveTo(i, rectangle.getBottom()).lineTo(i + rectangle.getHeight(), rectangle.getTop());
        canvas.stroke();

        signer.setFieldName("Advanced Electronic Signature");


        PrivateKeySignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, provider.getName());
        IExternalDigest digest = new BouncyCastleDigest();

        // Sign the document using the detached mode, CMS or CAdES equivalent.
        signer.signDetached(digest, pks, chain, null, null, null,
                0, PdfSigner.CryptoStandard.CMS);

        return finalDest;
    }
}
