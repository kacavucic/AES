package com.naprednoprogramiranje.aes.service.signingSession;

import com.naprednoprogramiranje.aes.model.SigningSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Manipulates a signing session details
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Service
public interface SigningSessionService {

    /**
     * Searches for the session with provided ID
     *
     * @param id ID of the session to search for
     *
     * @return Optional signing session as a search result
     */
    Optional<SigningSession> findById(String id);

    /**
     * Searches for the session based on provided filepath
     *
     * @param filepath Filepath used for searching
     *
     * @return Signing session as a search result. If no session was found for provided filepath, null will be returned
     */
    SigningSession findByFilepath(String filepath);

    /**
     * Saves a session in a database
     *
     * @param signingSession Signing session to be saved
     *
     * @return Saved signing session
     */
    SigningSession save(SigningSession signingSession);
}
