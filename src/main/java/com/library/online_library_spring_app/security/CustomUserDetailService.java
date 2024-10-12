package com.library.online_library_spring_app.security;

import com.library.online_library_spring_app.dao.entity.UserRole;
import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dao.repository.UserRoleRepository;
import com.library.online_library_spring_app.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//@Lazy
public class CustomUserDetailService implements UserDetailsService {
    private final UsersService usersService;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersService.findUserByUsername(username);//byPin
        List<UserRole> userRoles = userRoleRepository.findRolesByUserId(user.getId());
        List<String> authorities = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            authorities.add(String.valueOf(userRole.getRole().getRoleName()));
        }
        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), authorities);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Users user = usersService.findUserByUsername(username); //byPin
//        return new UserPrincipal(user.getId(), user.getUsername(), user.getPassword(), List.of("USER"));
//    }
}
