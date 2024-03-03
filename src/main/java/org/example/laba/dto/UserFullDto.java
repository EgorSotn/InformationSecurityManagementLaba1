package org.example.laba.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.laba.valid.ValidEmail;

import java.util.List;
@Data
public class UserFullDto {
    long id;
    @NotNull
    private String username;
    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;
    private String password;
    List<RoleDto> roles;
    UserSettingsDto userSettings;
}
