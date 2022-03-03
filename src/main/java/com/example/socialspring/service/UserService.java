package com.example.socialspring.service;

import com.example.socialspring.entities.User;
import com.example.socialspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("There no such user");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        return buildUser(user, grantedAuthorities);
    }

    private org.springframework.security.core.userdetails.User buildUser(User user, List<GrantedAuthority> grantedAuthorities) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                grantedAuthorities
        );
    }
}
