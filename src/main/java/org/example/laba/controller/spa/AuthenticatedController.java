package org.example.laba.controller.spa;

import lombok.RequiredArgsConstructor;
import org.example.laba.dto.UserDto;
import org.example.laba.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class AuthenticatedController {
    private final AppUserService userService;

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "login.html";
    }
    @GetMapping(value = "/login/error")
    public String getLoginPageError(@RequestParam("message") String error, Model model) {
        model.addAttribute("error", URLDecoder.decode(error, StandardCharsets.UTF_8));
        return "login.html";
    }
//    @RequestMapping("/login-error.html")
//    public String loginError(Model model) {
//        model.addAttribute("loginError", true);
//        return "login.html";
//    }
//    @PostMapping("/login")
//    public String editUser(@ModelAttribute("userForm") UserDto appUser) {
//        return "redirect:/user/edit";
//    }
}
