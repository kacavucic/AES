package com.naprednoprogramiranje.aes.web.controller.admin;

import com.naprednoprogramiranje.aes.model.Role;
import com.naprednoprogramiranje.aes.service.role.RoleService;
import com.naprednoprogramiranje.aes.web.model.RoleDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/adminPage")
public class RolesController {

    public static final String REDIRECT_ADMIN_PAGE_ROLES = "redirect:/adminPage/roles";

    private final RoleService roleService;

    @GetMapping("/roles")
    public ModelAndView showRoles() {
        ModelAndView modelAndView = new ModelAndView("adminPage/role/roles");
        modelAndView.addObject("roles", roleService.findAll());
        return modelAndView;
    }

    @GetMapping("/roles/newRole")
    public String getAddNewRoleForm(Model model) {
        model.addAttribute("newRole", new RoleDto()
        );
        return "adminPage/role/newRole";
    }

    @PostMapping("/roles/newRole")
    public String saveNewRole(@ModelAttribute("newRole") @Valid final Role newRole,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        boolean roleNameAlreadyExists = roleService.findByRoleName(newRole.getRoleName()) != null;
        boolean hasErrors = roleNameAlreadyExists || bindingResult.hasErrors();
        String formWithErrors = "adminPage/role/newRole";

        if (roleNameAlreadyExists) bindingResult.rejectValue("name", "roleNameAlreadyExists");
        if (hasErrors) return formWithErrors;

        roleService.save(newRole);
        redirectAttributes.addFlashAttribute("roleHasBeenSaved", true);
        return REDIRECT_ADMIN_PAGE_ROLES;
    }
}