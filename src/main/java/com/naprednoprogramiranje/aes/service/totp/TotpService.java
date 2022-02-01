package com.naprednoprogramiranje.aes.service.totp;


import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.exceptions.CodeGenerationException;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.springframework.stereotype.Component;

/**
 * Generates and validates OTC codes used in various scenarios
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Component
public class TotpService {

    private final CodeGenerator codeGenerator;
    private final CodeVerifier codeVerifier;

    /**
     * Default constructor
     */
    public TotpService() {
        this.codeGenerator = new DefaultCodeGenerator();
        this.codeVerifier = new DefaultCodeVerifier(codeGenerator, new SystemTimeProvider());
    }

    /**
     * Creates OTC code for provided secret using current timestamp and predefined time bucket
     *
     * @param secret Secret used for OTC generation
     *
     * @return Created OTC code with corrresponding details
     */
    public OTC getCodeObject(String secret) {
        try {
            long currentBucket = Math.floorDiv(new SystemTimeProvider().getTime(), 30);
            long timestamp = new SystemTimeProvider().getTime();
            String code = codeGenerator.generate(secret, currentBucket);
            OTC otc = new OTC();
            otc.setId(secret);
            otc.setTimestamp(timestamp);
            otc.setOtcCode(code);
            return otc;
        } catch (CodeGenerationException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Verifies provided code based on the provided secret
     *
     * @param secret Secret used for OTC code verification
     * @param code OTC code to be verified
     *
     * @return Whether provided code is valid or not
     */
    public boolean verifyCode(String secret, String code) {
        return codeVerifier.isValidCode(secret, code);
    }
}
