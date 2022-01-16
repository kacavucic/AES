package com.naprednoprogramiranje.aes.service.email;

import com.naprednoprogramiranje.aes.model.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {

    void sendRegistrationEmail(User user, String code) throws MessagingException;

    void sendSigningEmail(User user, String code) throws MessagingException;
}
