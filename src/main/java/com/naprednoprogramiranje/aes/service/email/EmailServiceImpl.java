package com.naprednoprogramiranje.aes.service.email;


import com.naprednoprogramiranje.aes.model.User;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private static final String NOREPLY_ADDRESS = "kacafon98@gmail.com";

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Override
    public void sendRegistrationEmail(User user, String code) throws MessagingException {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("code", code);

        String htmlBody = thymeleafTemplateEngine.process("registrationEmail.html", context);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(user.getEmail());
        helper.setSubject("[AES] Confirm Registration");
        helper.setText(htmlBody, true);
        emailSender.send(message);
    }

    @Override
    public void sendSigningEmail(User user, String code)
            throws MessagingException {


        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("code", code);

        String htmlBody = thymeleafTemplateEngine.process("signingEmail.html", context);

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(user.getEmail());
        helper.setSubject("[AES] Verify Document Signing");
        helper.setText(htmlBody, true);
        //helper.addInline("attachment.png", resourceFile);
        emailSender.send(message);
    }

}
