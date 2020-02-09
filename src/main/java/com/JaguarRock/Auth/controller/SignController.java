package com.JaguarRock.Auth.controller;

import com.JaguarRock.Auth.config.JwtTokenProvider;
import com.JaguarRock.Auth.exception.CEmailSigninFailedException;
import com.JaguarRock.Auth.model.User;
import com.JaguarRock.Auth.model.UserDTO;
import com.JaguarRock.Auth.model.response.CommonResult;
import com.JaguarRock.Auth.model.response.SingleResult;
import com.JaguarRock.Auth.repository.UserRepository;
import com.JaguarRock.Auth.service.ResponseService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/v1")
public class SignController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    public SignController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, ResponseService responseService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.responseService = responseService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/signin")
    public SingleResult<String> signin(@RequestParam String id, @RequestParam String password) {
        User user = userRepository.findByUid(id).orElseThrow(CEmailSigninFailedException::new);
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CEmailSigninFailedException();
        }
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));
    }

    @PostMapping(value = "/signup")
    public CommonResult signup(@RequestParam UserDTO userDTO) {
        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setRoles(Collections.singletonList("ROLE_USER"));
        userRepository.save(newUser);
        return responseService.getSuccessResult();
    }
}
