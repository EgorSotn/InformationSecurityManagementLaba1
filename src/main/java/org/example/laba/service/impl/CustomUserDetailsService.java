package org.example.laba.service.impl;

import lombok.AllArgsConstructor;
import org.example.laba.domain.AppUser;
import org.example.laba.domain.Role;
import org.example.laba.exception.InvalidValidPassword;
import org.example.laba.exception.NotFoundException;
import org.example.laba.exception.UserNotFoundException;
import org.example.laba.repository.AppUserRepository;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.laba.utils.NullUtils.getOrDefoult;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = userRepository.findAppUserByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if(getOrDefoult(() -> user.getUserSettings().isBlock(), false)) throw new LockedException("user block");
        return new User(
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));
        return authorities;
    }

//    @Override
//    public UserDetails updatePassword(UserDetails user, String newPassword) {
//        Optional<AppUser> appUserByEmail = userRepository.findAppUserByEmail(user.getUsername());
//        AppUser appUser = appUserByEmail.orElseThrow(() -> new NotFoundException("user" + user.getUsername()));
//        appUser.setPassword(newPassword);
//        return new User(appUser.getEmail(),
//                appUser.getPassword(),
//                getAuthorities(appUser.getRoles()));
//    }
}
