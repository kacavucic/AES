package com.naprednoprogramiranje.aes.web.controller.admin;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.service.user.UserService;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import com.naprednoprogramiranje.aes.web.service.UserDtoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/adminPage")
public class UsersController {

    public static final String REDIRECT_ADMIN_PAGE_USERS = "redirect:/adminPage/users";

    private final UserService userService;
    private final UserDtoService userDtoService;

    @GetMapping("/users")
    public ModelAndView getUsers(ModelAndView modelAndView) {
        modelAndView.setViewName("adminPage/user/users");
        return modelAndView;
    }
    @GetMapping("/users/newUser")
    public String getAddNewUserForm(Model model) {
        model.addAttribute("newUser", new UserDto());
        return "adminPage/user/newUser";
    }

    @PostMapping("/users/newUser")
    public String saveNewUser(@ModelAttribute("newUser") @Valid UserDto newUser,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws MessagingException {

        boolean emailExists = userService.findByEmail(newUser.getEmail()) != null;
        boolean usernameExists = userService.findByUsername(newUser.getUsername()) != null;
        boolean mobileExists = userService.findByMobile(newUser.getMobile()) != null;

        boolean validationFailed = emailExists || usernameExists || mobileExists || bindingResult.hasErrors();
        String formWithErrors = "adminPage/user/newUser";

        if (emailExists)
            bindingResult.rejectValue("email", "emailAlreadyExists");
        if (usernameExists)
            bindingResult.rejectValue("username", "usernameAlreadyExists");
        if (mobileExists)
            bindingResult.rejectValue("mobile", "mobileAlreadyExists");

        if (validationFailed) return formWithErrors;

        User user = userService.createNewAccount(newUser);
        user.setEnabled(true);

        userService.save(user);
        redirectAttributes.addFlashAttribute("userHasBeenSaved", true);
        return REDIRECT_ADMIN_PAGE_USERS;
    }

    @GetMapping("/users/editUser/{id}")
    public String getAddEditUserForm(Model model, @PathVariable Long id) {
        Optional<UserDto> editUser = userDtoService.findById(id);
        model.addAttribute("editUser", editUser.get());
        return "adminPage/user/editUser";
    }
    @PostMapping("/users/editUser")
    public String editUser(@ModelAttribute("editUser") @Valid UserDto editUser,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws MessagingException {

        User emailExists = userService.findByEmail(editUser.getEmail());
        User usernameExists = userService.findByUsername(editUser.getUsername());
        User mobileExists = userService.findByMobile(editUser.getMobile());


        if (emailExists != null && emailExists.getId() != editUser.getId()) {
            bindingResult.rejectValue("email", "emailAlreadyExists");
        }

        if (usernameExists != null && usernameExists.getId() != editUser.getId()) {
            bindingResult.rejectValue("username", "usernameAlreadyExists");
        }

        if (mobileExists != null && mobileExists.getId() != editUser.getId()) {
            bindingResult.rejectValue("mobile", "mobileAlreadyExists");
        }

        if (bindingResult.hasErrors()) {
            return "adminPage/user/editUser";
        } else {

            try {
                User user = userService.updateUser(editUser);
                userService.save(user);
            } catch (IllegalArgumentException e) {
                //
            }
            redirectAttributes.addFlashAttribute("userHasBeenEdited", true);
            return REDIRECT_ADMIN_PAGE_USERS;
        }

    }
}