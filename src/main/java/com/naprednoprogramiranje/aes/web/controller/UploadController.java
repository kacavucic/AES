package com.naprednoprogramiranje.aes.web.controller;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.naprednoprogramiranje.aes.model.SigningSession;
import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.service.email.EmailService;
import com.naprednoprogramiranje.aes.service.ipaddress.GeoIP;
import com.naprednoprogramiranje.aes.service.ipaddress.IPLocationService;
import com.naprednoprogramiranje.aes.service.ipaddress.RequestService;
import com.naprednoprogramiranje.aes.service.signing.SigningService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Optional;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("")
public class UploadController {

    private EmailService emailService;
    private TotpService totpService;
    private StorageService storageService;
    private SigningSessionService signingSessionService;
    private RequestService requestService;
    private IPLocationService locationService;

    @PostMapping(value = "/upload-document")
    public ModelAndView uploadDocument(ModelAndView modelAndView, @RequestParam("document") MultipartFile document,
                                       BindingResult bindingResult, Authentication authentication) throws MessagingException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("website/index");
        } else {
            Path filepath = storageService.store(document);

            String secret = UUID.randomUUID().toString();
            OTC otc = totpService.getCodeObject(secret);
            SigningSession signingSession = SigningSession.builder()
                    .id(secret)
                    .filepath(filepath.toAbsolutePath().toString())
                    .otcCode(otc.getOtcCode())
                    .timestamp(otc.getTimestamp())
                    .build();

            emailService.sendSigningEmail(user, signingSession.getOtcCode());

            SigningSession signingSessionSaved = signingSessionService.save(signingSession);

            SigningSessionDto signingSessionDto = new SigningSessionDto();
            signingSessionDto.setId(signingSessionSaved.getId());
            modelAndView.addObject("signingSessionDto", signingSessionDto);

            modelAndView.setViewName("website/signing/verifySigning");
        }
        return modelAndView;
    }

    @PostMapping(value = "/verify-signing")
    public ModelAndView verifySigning(ModelAndView modelAndView,
                                      @ModelAttribute("signingSessionDto") @Valid final SigningSessionDto signingSessionDto,
                                      BindingResult bindingResult, Authentication authentication, HttpServletRequest request) throws IOException, GeoIp2Exception, GeneralSecurityException {


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (user == null) {
            System.out.println("User not found");
        }

        assert user != null;
        boolean codeVerified = totpService.verifyCode(signingSessionDto.getId(), signingSessionDto.getOtcCode());

        if (!codeVerified) {
            bindingResult.rejectValue("otcCode", "invalidOTC");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("website/signing/verifySigning");
        } else {

            Optional<SigningSession> signingSession = signingSessionService.findById(signingSessionDto.getId());
            if (signingSession.isPresent()) {
                SigningSession session = signingSession.get();

                String filepathToLoad = session.getFilepath();
                Path src = storageService.load(filepathToLoad);

                String clientIp = requestService.getClientIp(request);
                GeoIP geoIP = locationService.getLocation("87.116.160.153");
                String location = geoIP.getCity();

                File signingFile = new File(session.getFilepath());
                HashCode hash = Files.hash(signingFile, Hashing.md5());

                String reason = "On behalf of " + user.getFirstName() + " " + user.getLastName() + ", " + user.getEmail() + "\n"
                        + "Using OTC " + session.getOtcCode() + " and timestamp " + session.getTimestamp() + "\n" +
                        "Hash value of document: " + hash;

                SigningService signingService = new SigningService();
                String dest = signingService.sign(src, reason, location);
                File signed = new File(dest);
                ///////////////////////////////////////////////////
                modelAndView.addObject("confirmationMessage", user.getFirstName() + ", AES has successfully signed document " + src.getFileName().toString() + " on your behalf!");
                modelAndView.addObject("documentPath", signed.getName());
                modelAndView.setViewName("website/signing/signed");
            }

        }
        return modelAndView;
    }
}
