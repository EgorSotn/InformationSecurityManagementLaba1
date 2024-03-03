package org.example.laba.mapper;

import org.example.laba.domain.AppUser;
import org.example.laba.domain.Role;
import org.example.laba.domain.UserSettings;
import org.example.laba.dto.RoleDto;
import org.example.laba.dto.UserDto;
import org.example.laba.dto.UserFullDto;
import org.example.laba.dto.UserSettingsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto mapToDto(AppUser appUser);
    AppUser map(UserDto userDto);

    UserFullDto mapToFullDto(AppUser appUser);

    RoleDto mapRole(Role role);
    UserSettings mapToUserSettingsDto(UserSettingsDto userSettingsDto);
}
