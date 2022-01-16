package com.naprednoprogramiranje.aes.web.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/adminPage")
public class UsersController {

    @GetMapping("/users")
    public ModelAndView getUsers(ModelAndView modelAndView) {
        modelAndView.setViewName("adminPage/user/users");
        return modelAndView;
    }
}