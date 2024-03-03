package org.example.laba.controller.spa;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.laba.dto.PasswordDto;
import org.example.laba.dto.RoleDto;
import org.example.laba.dto.UserFullDto;
import org.example.laba.exception.InvalidOldPasswordException;
import org.example.laba.exception.InvalidValidPassword;
import org.example.laba.service.AppUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PrivateController {
    private final AppUserService appUserService;

    @GetMapping("/user/edit")
    public String editPage(Model model, Authentication authentication) {
        UserFullDto user = appUserService.getUser(authentication.getName());
        model.addAttribute("userDto", user);
        model.addAttribute("isAdmin", user.getRoles().stream().map(RoleDto::getName).toList().contains("ROLE_ADMIN"));

        return "edit";
    }

    @GetMapping("/user/edit/{id}")
    public String editPage(@PathVariable("id") Long id, Authentication authentication, Model model) {
        UserFullDto currentUser = appUserService.getUser(authentication.getName());
        UserFullDto user = appUserService.getUserById(id);
        model.addAttribute("userDto", user);
        model.addAttribute("isOtherUser", true);
        model.addAttribute("isAdmin", currentUser.getRoles().stream().map(RoleDto::getName).toList().contains("ROLE_ADMIN"));
        return "edit";
    }

    @GetMapping("/user/change-password/{id}")
    public ModelAndView showChangePasswordPage(@PathVariable Long id, final Model model) {
        model.addAttribute("passwordDto", new PasswordDto());
        model.addAttribute("userId", id);
        return new ModelAndView("updatePassword");
    }

    @PostMapping("/user/change-password/{id}")
    public String changePassword(@PathVariable Long id, @Valid @ModelAttribute PasswordDto passwordDto, Model model) {
        try {
            appUserService.changePassword(id, passwordDto);
        } catch (InvalidOldPasswordException | InvalidValidPassword e) {
            model.addAttribute("message", e.getMessage());
            return "updatePassword";
        }

        return "redirect:/user/edit";
    }
//    @RequestMapping("/user/edit")
//    public String editPage(Model model) {
////        model.addAttribute("userDto", privateService.getUserById(id));
//        return "edit";
//    }
//    @PostMapping("/user/updatePassword")
//    public GenericResponse changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) {
////        final User user = userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
//        if (!privateService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
//            throw new InvalidOldPasswordException();
//        }
//        privateService.changeUserPassword(user, passwordDto.getNewPassword());
//        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
//    }
}
