package com.naprednoprogramiranje.aes.service.signingSession;

import com.naprednoprogramiranje.aes.model.SigningSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SigningSessionService {

    Optional<SigningSession> findById(String id);

    SigningSession findByFilepath(String filepath);

    SigningSession save(SigningSession signingSession);
}
