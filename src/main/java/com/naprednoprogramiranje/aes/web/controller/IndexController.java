package com.naprednoprogramiranje.aes.web.controller;

import com.naprednoprogramiranje.aes.web.model.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "website/index";
    }

    @GetMapping("/login")
    public String login() {
        return "website/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "website/registration/register";
    }
}
