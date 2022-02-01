package com.naprednoprogramiranje.aes.service.email;

import com.naprednoprogramiranje.aes.model.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Email service used for sending registration and signing mails
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Service
public interface EmailService {

    /**
     * Send registration mail relying on predefined template
     *
     * @param user User to whom email should be sent
     * @param code OTC code used for registration
     *
     * @throws MessagingException If exception is thrown by MimeMessageHelper
     */
    void sendRegistrationEmail(User user, String code) throws MessagingException;

    /**
     * Send signing mail relying on predefined template
     *
     * @param user User to whom email should be sent
     * @param code OTC code used for signing
     *
     * @throws MessagingException If exception is thrown by MimeMessageHelper
     */
    void sendSigningEmail(User user, String code) throws MessagingException;
}
