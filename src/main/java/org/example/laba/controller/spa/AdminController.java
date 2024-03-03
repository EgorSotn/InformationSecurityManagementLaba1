package org.example.laba.controller.spa;

import lombok.RequiredArgsConstructor;
import org.example.laba.dto.UserDto;
import org.example.laba.dto.UserFullDto;
import org.example.laba.dto.UserSettingsDto;
import org.example.laba.service.AdminService;
import org.example.laba.service.AppUserService;
import org.example.laba.service.EncodingKeyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final AppUserService userService;
    private final EncodingKeyService encodingKeyService;

    @GetMapping("/user")
    public String getAllUser(Model model) {
        model.addAttribute("usersDto", adminService.getUsers());
        return "users";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute("userDto") UserDto userDto) {
        UserFullDto userFullDto = userService.saveAppUser(userDto);
        return "redirect:/user/edit/" + userFullDto.getId();
    }

    @GetMapping("/user/add")
    public String getHtmlCreateUser(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "create_user";
    }

    @PostMapping("/user/edit/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        adminService.deleteUser(id);
        return "redirect:/admin/user";
    }

    @ResponseBody
    @PostMapping("/user/edit/update-settings")
    public ResponseEntity<?> updateUserSettings(@RequestBody UserSettingsDto userSettingsDto) {
        UserFullDto userFullDto = adminService.updateUserSettings(userSettingsDto);
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/user/edit/" + userFullDto.getId());
        return ResponseEntity.ok(response);
    }
    @PostMapping("/set-key")
    public String setKey(@RequestParam("key") String key) {
        encodingKeyService.createKey(key);
        return "redirect:/admin/user";
    }
    @GetMapping("/set-key")
    public String setKeyView() {
        return "SetKey.html";
    }
}
