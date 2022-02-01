package com.naprednoprogramiranje.aes.service.signingSession;

import com.naprednoprogramiranje.aes.model.SigningSession;
import com.naprednoprogramiranje.aes.repository.SigningSessionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class SigningSessionServiceImplTest {

    @Captor
    ArgumentCaptor<SigningSession> captor;
    @Mock
    SigningSessionRepository signingSessionRepository;
    @InjectMocks
    SigningSessionServiceImpl signingSessionService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testFindById() {
        when(signingSessionRepository.findById("SESSION_ONE"))
                .thenReturn(Optional.ofNullable(SigningSession.builder()
                        .id("SESSION_SECRET")
                        .otcCode("123456")
                        .filepath("SESSION_ONE_FILEPATH")
                        .timestamp(System.currentTimeMillis())
                        .build()));
        when(signingSessionRepository.findById("SESSION_TWO"))
                .thenReturn(Optional.empty());
        Optional<SigningSession> one = signingSessionService.findById("SESSION_ONE");
        Optional<SigningSession> two = signingSessionService.findById("SESSION_TWO");
        assertTrue(one.isPresent());
        assertFalse(two.isPresent());
    }

    @Test
    public void testFindByFilepath() {
        when(signingSessionRepository.findByFilepath(eq("FP_1")))
                .thenReturn(SigningSession.builder()
                        .filepath("FP_1")
                        .build());
        when(signingSessionRepository.findByFilepath(eq("FP_2")))
                .thenReturn(null);
        SigningSession one = signingSessionService.findByFilepath("FP_1");
        SigningSession two = signingSessionService.findByFilepath("FP_2");
        assertNotNull(one);
        assertNull(two);
    }

    @Test
    public void testSave() {
        SigningSession session1 = SigningSession.builder()
                .id("ROLE_KACA")
                .build();
        signingSessionService.save(session1);

        verify(signingSessionRepository, times(1)).save(captor.capture());
        SigningSession session2 = captor.getValue();
        assertNotNull(session1);
        assertNotNull(session2);
        assertEquals(session1.getId(),session2.getId());
    }

}
