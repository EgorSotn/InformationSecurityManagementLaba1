package org.example.laba.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.laba.domain.AppUser;
import org.example.laba.domain.Role;
import org.example.laba.domain.UserSettings;
import org.example.laba.dto.PasswordDto;
import org.example.laba.dto.UserDto;
import org.example.laba.dto.UserFullDto;
import org.example.laba.exception.InvalidOldPasswordException;
import org.example.laba.exception.InvalidValidPassword;
import org.example.laba.exception.NotFoundException;
import org.example.laba.exception.UserAlreadyExistException;
import org.example.laba.mapper.UserMapper;
import org.example.laba.repository.AppUserRepository;
import org.example.laba.repository.RoleRepository;
import org.example.laba.service.AppUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static org.example.laba.utils.NullUtils.getOrDefoult;


@RequiredArgsConstructor
@Service
@Slf4j
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserFullDto saveAppUser(UserDto userDto) {
        log.info("Saving new user {}", userDto.getEmail());
        appUserRepository.findAppUserByEmail(userDto.getEmail()).ifPresent((u) -> {
                    throw new UserAlreadyExistException("пользователь уже существует");
                }
        );
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findRoleByName("ROLE_USER").orElseGet(() -> {
            Role a = new Role();
            a.setName("USER");
            return roleRepository.save(a);
        });
        AppUser map = userMapper.map(userDto);
        map.getRoles().add(role);
//        map.setUserSettings(new UserSettings());
        return userMapper.mapToFullDto(appUserRepository.save(map));
    }

    @Override
    public UserFullDto getUser(String email) {
        log.info("Getting user by email {} ", email);
        return userMapper.mapToFullDto(appUserRepository.findAppUserByEmail(email).orElseThrow(() -> new NotFoundException(email)));
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepository.save(role);
    }


    @Override
    @Transactional
    public void addRoleToAppUser(String email, String roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        AppUser appUser = appUserRepository.findAppUserByEmail(email).orElseThrow(() -> new NotFoundException(email));
        Role role = roleRepository.findRoleByName(roleName).orElseGet(() -> {
            Role a = new Role();
            a.setName(roleName);
            return roleRepository.save(a);
        });
        appUser.getRoles().add(role);
    }

    @Override
    @Transactional
    public UserFullDto changePassword(Long id, PasswordDto passwordDto) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id)));
        if (!passwordDto.getConfirmPassword().equals(passwordDto.getNewPassword()))
            throw new InvalidOldPasswordException("no password match");
        else if (passwordEncoder.matches(appUser.getPassword(), passwordDto.getCurrentPassword()))
            throw new InvalidOldPasswordException("error: current and new password equals");
        else if(getOrDefoult(() -> appUser.getUserSettings().isRestrictionPassword(), false)) {
            Pattern pattern = Pattern.compile(getOrDefoult(() -> appUser.getUserSettings().getMatchPassword(), "^.*$"));
            if (!pattern.matcher(passwordDto.getNewPassword()).matches()) throw new InvalidValidPassword("incorrect format password ");
        }
        appUser.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        return userMapper.mapToFullDto(appUserRepository.save(appUser));
    }

    @Override
    public UserFullDto getUserById(long id) {
        log.info("Getting user by id {} ", id);
        return userMapper.mapToFullDto(appUserRepository.findById(id).orElseThrow(() -> new NotFoundException(String.valueOf(id))));
    }
}
