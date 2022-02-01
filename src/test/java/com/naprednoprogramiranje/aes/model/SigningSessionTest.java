package com.naprednoprogramiranje.aes.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class SigningSessionTest {

    SigningSession signingSession;

    @AfterEach
    public void tearDown() {
        signingSession = null;
    }

    @Test
    public void testSigningSession() {
        long ts = System.currentTimeMillis();
        signingSession = SigningSession.builder()
                .id("SESSION_SECRET")
                .otcCode("123456")
                .filepath("SESSION_ONE_FILEPATH")
                .timestamp(ts)
                .build();
        assertNotNull(signingSession);
        assertEquals(signingSession.getId(), "SESSION_SECRET");
        assertEquals(signingSession.getOtcCode(), "123456");
        assertEquals(signingSession.getFilepath(), "SESSION_ONE_FILEPATH");
        assertEquals(signingSession.getTimestamp(), ts);
    }

    @Test
    public void testEquals() {
        long ts = System.currentTimeMillis();
        signingSession = SigningSession.builder()
                .id("SESSION_SECRET")
                .otcCode("123456")
                .filepath("SESSION_ONE_FILEPATH")
                .timestamp(ts)
                .build();

        SigningSession signingSessionCmp = SigningSession.builder()
                .id("SESSION_SECRET")
                .otcCode("123456")
                .filepath("SESSION_ONE_FILEPATH")
                .timestamp(ts)
                .build();
        assertNotNull(signingSession);
        assertNotNull(signingSessionCmp);
        assertEquals(signingSession, signingSessionCmp);
    }

    @ParameterizedTest
    @CsvSource({
            "SESSION_SECRET2, 123456, SESSION_ONE_FILEPATH, 5555555",
            "SESSION_SECRET, 123457, SESSION_ONE_FILEPATH, 5555555",
            "SESSION_SECRET, 123456, SESSION_ONE_FILEPATH2, 5555555",
            "SESSION_SECRET, 123456, SESSION_ONE_FILEPATH, 5555556",
    })
    public void testEqualsDifferent(String id, String otc, String filepath, long ts) {
        signingSession = SigningSession.builder()
                .id("SESSION_SECRET")
                .otcCode("123456")
                .filepath("SESSION_ONE_FILEPATH")
                .timestamp(System.currentTimeMillis())
                .build();

        SigningSession signingSessionCmp = SigningSession.builder()
                .id(id)
                .otcCode(otc)
                .filepath(filepath)
                .timestamp(ts)
                .build();
        assertNotNull(signingSession);
        assertNotNull(signingSessionCmp);
        assertNotEquals(signingSession, signingSessionCmp);
    }

    @Test
    public void testHashCode() {
        long ts = System.currentTimeMillis();
        signingSession = SigningSession.builder()
                .id("SESSION_SECRET")
                .otcCode("123456")
                .filepath("SESSION_ONE_FILEPATH")
                .timestamp(ts)
                .build();

        SigningSession signingSessionCmp = SigningSession.builder()
                .id("SESSION_SECRET")
                .otcCode("123456")
                .filepath("SESSION_ONE_FILEPATH")
                .timestamp(ts)
                .build();
        assertNotNull(signingSession);
        assertNotNull(signingSessionCmp);
        assertEquals(signingSession.hashCode(), signingSessionCmp.hashCode());
    }

    @Test
    public void testHashCodeDifferent() {
        long ts = System.currentTimeMillis();
        signingSession = SigningSession.builder()
                .id("SESSION_SECRET")
                .otcCode("123456")
                .filepath("SESSION_ONE_FILEPATH")
                .timestamp(ts)
                .build();

        SigningSession signingSessionCmp = SigningSession.builder()
                .id("SESSION_SECRET2")
                .otcCode("123452")
                .filepath("SESSION_ONE_FILEPATH2")
                .timestamp(ts)
                .build();
        assertNotNull(signingSession);
        assertNotNull(signingSessionCmp);
        assertNotEquals(signingSession.hashCode(), signingSessionCmp.hashCode());
    }
}
