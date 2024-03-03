package org.example.laba.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.laba.valid.ValidEmail;
import org.example.laba.valid.ValidPassword;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull
    private String username;
    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;
    @ValidPassword
    private String password;
}
