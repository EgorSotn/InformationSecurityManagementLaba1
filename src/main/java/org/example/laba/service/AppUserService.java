package org.example.laba.service;


import org.example.laba.domain.Role;
import org.example.laba.dto.PasswordDto;
import org.example.laba.dto.UserDto;
import org.example.laba.dto.UserFullDto;

public interface AppUserService {
    UserFullDto getUserById(long id);
    UserFullDto getUser(String email);

    UserFullDto saveAppUser(UserDto userDto);

    Role saveRole(Role role);

    void addRoleToAppUser(String email, String roleName);
    UserFullDto changePassword(Long id, PasswordDto passwordDto);
}
