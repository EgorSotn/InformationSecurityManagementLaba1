package org.example.laba.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laba.domain.AppUser;
import org.example.laba.domain.UserSettings;
import org.example.laba.dto.UserDto;
import org.example.laba.dto.UserFullDto;
import org.example.laba.dto.UserSettingsDto;
import org.example.laba.exception.NotFoundException;
import org.example.laba.mapper.UserMapper;
import org.example.laba.repository.AppUserRepository;
import org.example.laba.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AppUserRepository appUserRepository;
    private final UserMapper userMapper;


    @Override
    public List<UserFullDto> getUsers() {
        log.info("find users");
        return appUserRepository.findAll().stream().map(userMapper::mapToFullDto).toList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserFullDto updateUserSettings(UserSettingsDto userSettingsDto) {
        log.info("Getting user by id {} ", userSettingsDto.getUserId());
        AppUser appUser = appUserRepository.findById(userSettingsDto.getUserId()).orElseThrow(() -> new NotFoundException(String.valueOf(userSettingsDto.getUserId())));
        if (appUser.getUserSettings() == null) {
            appUser.setUserSettings(userMapper.mapToUserSettingsDto(userSettingsDto));
        } else {
            UserSettings userSettings = appUser.getUserSettings();
            userSettings.setBlock(userSettingsDto.isBlock());
            userSettings.setMatchPassword(userSettingsDto.getMatchPassword());
            userSettings.setRestrictionPassword(userSettingsDto.isRestrictionPassword());
        }
        return userMapper.mapToFullDto(appUserRepository.save(appUser));
    }

    @Override
    public void deleteUser(long id) {
        log.info("Delete user by id {} ", id);
        appUserRepository.delete(appUserRepository.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id))));
    }

}
