package com.naprednoprogramiranje.aes.repository;

import com.naprednoprogramiranje.aes.model.SigningSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SigningSessionRepository extends JpaRepository<SigningSession, String> {
    SigningSession findByFilepath(String filepath);
}
