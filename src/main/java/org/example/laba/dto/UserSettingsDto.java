package org.example.laba.dto;

import lombok.Data;

@Data
public class UserSettingsDto {
    Long userId;
    boolean block;
    String matchPassword;
    boolean restrictionPassword;
}
