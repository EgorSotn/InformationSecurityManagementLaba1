package org.example.laba.dto;

import lombok.Data;
import org.example.laba.valid.ValidPassword;

@Data
public class PasswordDto {

    private String currentPassword;

//    @ValidPassword
    private String newPassword;

//    @ValidPassword
    private String confirmPassword;
}
