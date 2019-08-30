package com.ocha.boc.security.jwt;

import com.ocha.boc.entity.User;
import com.ocha.boc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phone)
            throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByPhone(phone).orElse(null));
        if (!Objects.isNull(user)) {
            return UserPrincipal.createCMS(user.get());
        }
        return null;
    }

}