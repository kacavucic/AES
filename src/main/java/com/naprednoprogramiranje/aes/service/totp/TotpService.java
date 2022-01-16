package com.naprednoprogramiranje.aes.service.totp;


import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.springframework.stereotype.Component;

@Component
public class TotpService {

    private final CodeGenerator codeGenerator;
    private final CodeVerifier codeVerifier;

    public TotpService() {
        this.codeGenerator = new DefaultCodeGenerator();
        this.codeVerifier = new DefaultCodeVerifier(codeGenerator, new SystemTimeProvider());

    }

    public String getCode(String secret) {
        try {
            long currentBucket = Math.floorDiv(new SystemTimeProvider().getTime(), 30);
            return codeGenerator.generate(secret, currentBucket);
        } catch (CodeGenerationException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public OTC getCodeObject(String secret) {
        try {
            long timestamp = new SystemTimeProvider().getTime();
            long currentBucket = Math.floorDiv(timestamp, 30);
            OTC otc = new OTC();
            otc.setId(secret);
            otc.setTimestamp(timestamp);
            otc.setOtcCode(codeGenerator.generate(secret, currentBucket));
            return otc;
        } catch (CodeGenerationException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean verifyCode(String secret, String code) {
        return codeVerifier.isValidCode(secret, code);
    }
}
