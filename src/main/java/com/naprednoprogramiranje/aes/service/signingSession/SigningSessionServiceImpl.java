package com.naprednoprogramiranje.aes.service.signingSession;

import com.naprednoprogramiranje.aes.model.SigningSession;
import com.naprednoprogramiranje.aes.repository.SigningSessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class SigningSessionServiceImpl implements SigningSessionService {

    private final SigningSessionRepository signingSessionRepository;

    @Override
    public Optional<SigningSession> findById(String id) {
        return signingSessionRepository.findById(id);
    }

    @Override
    public SigningSession findByFilepath(String filepath) {
        return signingSessionRepository.findByFilepath(filepath);
    }

    @Override
    public SigningSession save(SigningSession signingSession) {
        return signingSessionRepository.save(signingSession);
    }
}
