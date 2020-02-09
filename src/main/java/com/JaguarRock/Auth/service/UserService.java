package com.JaguarRock.Auth.service;

import com.JaguarRock.Auth.exception.CUserNotFoundException;
import com.JaguarRock.Auth.model.User;
import com.JaguarRock.Auth.model.UserDTO;
import com.JaguarRock.Auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String userPK) {
        return userRepository.findById(Long.valueOf(userPK)).orElseThrow(CUserNotFoundException::new);
    }
}
