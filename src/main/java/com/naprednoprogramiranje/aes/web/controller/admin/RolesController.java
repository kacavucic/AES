package com.naprednoprogramiranje.aes.web.controller.admin;

import com.naprednoprogramiranje.aes.service.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/adminPage")
public class RolesController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public ModelAndView showRoles() {
        ModelAndView modelAndView = new ModelAndView("adminPage/role/roles");
        modelAndView.addObject("roles", roleService.findAll());
        return modelAndView;
    }
}