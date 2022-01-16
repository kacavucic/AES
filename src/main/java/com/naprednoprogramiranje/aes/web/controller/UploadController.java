package com.naprednoprogramiranje.aes.web.controller;

import com.naprednoprogramiranje.aes.model.SigningSession;
import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.service.email.EmailService;
import com.naprednoprogramiranje.aes.service.signingSession.SigningSessionService;
import com.naprednoprogramiranje.aes.service.storage.StorageService;
import com.naprednoprogramiranje.aes.service.totp.OTC;
import com.naprednoprogramiranje.aes.service.totp.TotpService;
import com.naprednoprogramiranje.aes.service.userDetails.UserDetailsImpl;
import com.naprednoprogramiranje.aes.web.model.SigningSessionDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.nio.file.Path;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("")
public class UploadController {

    private EmailService emailService;
    private TotpService totpService;
    private StorageService storageService;
    private SigningSessionService signingSessionService;

    @PostMapping(value = "/upload-document")
    public ModelAndView uploadDocument(ModelAndView modelAndView, @RequestParam("document") MultipartFile document,
                                       BindingResult bindingResult, Authentication authentication) throws MessagingException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("website/index");
        } else {
            Path filepath = storageService.store(document);

            SigningSession signingSession = new SigningSession();

            String secret = UUID.randomUUID().toString();
            OTC otc = totpService.getCodeObject(secret);

            signingSession.setId(otc.getId());
            signingSession.setOtcCode(otc.getOtcCode());
            signingSession.setTimestamp(otc.getTimestamp());
            signingSession.setFilepath(filepath.toAbsolutePath().toString());

            emailService.sendSigningEmail(user, signingSession.getOtcCode());

            SigningSession signingSessionSaved = signingSessionService.save(signingSession);

            SigningSessionDto signingSessionDto = new SigningSessionDto();
            signingSessionDto.setId(signingSessionSaved.getId());
            modelAndView.addObject("signingSessionDto", signingSessionDto);

            modelAndView.setViewName("website/signing/verifySigning");
        }
        return modelAndView;
    }

}
