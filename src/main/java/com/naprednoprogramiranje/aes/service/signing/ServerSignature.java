package com.naprednoprogramiranje.aes.service.signing;

import com.itextpdf.signatures.IExternalSignature;

import java.security.GeneralSecurityException;

public class ServerSignature implements IExternalSignature {
    @Override
    public String getHashAlgorithm() {
        return null;
    }

    @Override
    public String getEncryptionAlgorithm() {
        return null;
    }

    @Override
    public byte[] sign(byte[] bytes) throws GeneralSecurityException {
        return new byte[0];
    }
}
