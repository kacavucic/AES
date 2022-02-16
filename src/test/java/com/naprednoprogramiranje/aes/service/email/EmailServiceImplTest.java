package com.naprednoprogramiranje.aes.service.email;

import com.naprednoprogramiranje.aes.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.IContext;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceImplTest {

    @Captor
    ArgumentCaptor<MimeMessage> emailCaptor;
    @Mock
    JavaMailSender mailSender;
    @Mock
    ITemplateEngine templateEngine;
    @InjectMocks
    EmailServiceImpl emailService;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

    @Test
    void sendRegistrationEmail() throws MessagingException {
        when(templateEngine.process(eq("registrationEmail.html"), any(IContext.class)))
                .thenReturn("Registration mail content");
        when(mailSender.createMimeMessage())
                .thenReturn(new MimeMessage(Session.getInstance(new Properties())));
        User user = User.builder()
                .email("test@mail.com")
                .firstName("First")
                .lastName("Last")
                .username("username")
                .mobile("0601234567")
                .password("Abc123123!")
                .build();

        emailService.sendRegistrationEmail(user, "123");

        verify(mailSender, times(1)).send(emailCaptor.capture());

        MimeMessage msg = emailCaptor.getValue();
        assertNotNull(msg);
        assertTrue(msg.getAllRecipients().length == 1);
        assertEquals(msg.getAllRecipients()[0].toString(), "test@mail.com");
    }

    @Test
    void sendSigningEmail() throws MessagingException {
        when(templateEngine.process(eq("signingEmail.html"), any(IContext.class)))
                .thenReturn("Signing mail content");
        when(mailSender.createMimeMessage())
                .thenReturn(new MimeMessage(Session.getInstance(new Properties())));
        User user = User.builder()
                .email("test@mail.com")
                .firstName("First")
                .lastName("Last")
                .username("username")
                .mobile("0601234567")
                .password("Abc123123!")
                .build();
        emailService.sendSigningEmail(user, "123");
        verify(mailSender, times(1)).send(emailCaptor.capture());

        MimeMessage msg = emailCaptor.getValue();
        assertNotNull(msg);
        assertEquals(msg.getAllRecipients().length, 1);
        assertEquals(msg.getAllRecipients()[0].toString(), "test@mail.com");
    }
}