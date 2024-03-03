package org.example.laba.controller.spa;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.laba.domain.AppUser;
import org.example.laba.dto.UserDto;
import org.example.laba.dto.UserFullDto;
import org.example.laba.exception.UserAlreadyExistException;
import org.example.laba.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthorizeController {
    private final AppUserService userService;

    @GetMapping("/registered")
    public String registered(Model model) {
        model.addAttribute("user", new AppUser());
        return "registered";
    }

    @PostMapping("/registered")
    public ModelAndView addUser(@Valid @ModelAttribute("userForm") UserDto userDto, Model model) {
        try {
            UserFullDto saveAppUser = userService.saveAppUser(userDto);
            model.addAttribute("user", userDto);
        }catch (UserAlreadyExistException e){
            ModelAndView mav = new ModelAndView("registration", "user", userDto);
            mav.addObject("error", e.getMessage());
        }
        return new ModelAndView("successRegister", "userDto", userDto);
    }

}
