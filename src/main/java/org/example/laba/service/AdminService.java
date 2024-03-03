package org.example.laba.service;

import org.example.laba.dto.UserDto;
import org.example.laba.dto.UserFullDto;
import org.example.laba.dto.UserSettingsDto;

import java.util.List;

public interface AdminService {


    List<UserFullDto> getUsers();

    UserFullDto updateUserSettings(UserSettingsDto userSettingsDto);

    void deleteUser(long id);

}
