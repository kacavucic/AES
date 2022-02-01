package com.naprednoprogramiranje.aes.service.totp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TotpServiceTest {

    TotpService service;

    @Test
    public void testService() {
        service = new TotpService();
        String secret = "SOME_SECRET";
        OTC otc = service.getCodeObject(secret);
        assertNotNull(otc);
        assertEquals(otc.getId(), secret);
        assertNotNull(otc.getOtcCode());
        boolean codeValid = service.verifyCode(secret, otc.getOtcCode());
        assertTrue(codeValid);
        boolean codeInvalid = service.verifyCode("SOME_OTHER_SECRET", otc.getOtcCode());
        assertFalse(codeInvalid);
    }
}
