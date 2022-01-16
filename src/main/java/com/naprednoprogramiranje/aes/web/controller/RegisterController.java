package com.naprednoprogramiranje.aes.web.controller;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.service.email.EmailService;
import com.naprednoprogramiranje.aes.service.totp.TotpService;
import com.naprednoprogramiranje.aes.service.user.UserService;
import com.naprednoprogramiranje.aes.web.model.CodeVerificationDto;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("")
public class RegisterController {

    private UserService userService;
    private EmailService emailService;
    private TotpService totpService;

    @PostMapping(value = "/submit-registration")
    public ModelAndView saveUser(ModelAndView modelAndView, @ModelAttribute("userDto") @Valid final UserDto userDto,
                                 BindingResult bindingResult) throws MessagingException {

        User emailExists = userService.findByEmail(userDto.getEmail());
        User userNameExists = userService.findByUsername(userDto.getUsername());
        User mobileExists = userService.findByMobile(userDto.getMobile());


        if (emailExists != null) {
            modelAndView.setViewName("website/registration/register");
            bindingResult.rejectValue("email", "emailAlreadyExists");
        }

        if (userNameExists != null) {
            modelAndView.setViewName("website/registration/register");
            bindingResult.rejectValue("username", "usernameAlreadyExists");
        }

        if (mobileExists != null) {
            modelAndView.setViewName("website/registration/register");
            bindingResult.rejectValue("mobile", "mobileAlreadyExists");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("website/registration/register");
        } else {
            User user = userService.createNewAccount(userDto);
            user.setEnabled(false);
            userService.save(user);
            String code = totpService.getCode(user.getEmail());
            emailService.sendRegistrationEmail(user, code);
            CodeVerificationDto codeVerificationDto = new CodeVerificationDto();
            codeVerificationDto.setUsername(user.getUsername());
            modelAndView.addObject("codeVerificationDto", codeVerificationDto);
            modelAndView.setViewName("website/registration/verifyRegistration");
        }

        return modelAndView;
    }
}
