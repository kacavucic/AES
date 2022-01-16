package com.naprednoprogramiranje.aes.web.controller.admin;

import com.naprednoprogramiranje.aes.model.User;
import com.naprednoprogramiranje.aes.service.user.UserService;
import com.naprednoprogramiranje.aes.web.model.UserDto;
import com.naprednoprogramiranje.aes.web.service.UserDtoService;
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

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/adminPage")
public class UsersController {

    public static final String REDIRECT_ADMIN_PAGE_USERS = "redirect:/adminPage/users";

    private final UserService userService;

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
}