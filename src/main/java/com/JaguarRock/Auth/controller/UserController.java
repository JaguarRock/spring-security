package com.JaguarRock.Auth.controller;

import com.JaguarRock.Auth.exception.CUserExistException;
import com.JaguarRock.Auth.exception.CUserNotFoundException;
import com.JaguarRock.Auth.model.User;
import com.JaguarRock.Auth.model.UserDTO;
import com.JaguarRock.Auth.model.response.CommonResult;
import com.JaguarRock.Auth.model.response.ListResult;
import com.JaguarRock.Auth.model.response.SingleResult;
import com.JaguarRock.Auth.repository.UserRepository;
import com.JaguarRock.Auth.service.ResponseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
public class UserController {

    private final UserRepository userRepository;
    private final ResponseService responseService;

    public UserController(UserRepository userRepository, ResponseService responseService) {
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    @GetMapping(value = "/users")
    public ListResult<User> findAllUser() {
        return responseService.getListResult(userRepository.findAll());
    }

    @GetMapping(value = "/users")
    public SingleResult<User> findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        return responseService.getSingleResult(userRepository.findByUid(id).orElseThrow(CUserNotFoundException::new));
    }

    /*@PutMapping(value = "/user")
    public SingleResult<UserDTO> modify(@RequestBody UserDTO userDTO) {
        User newUser = findUser();
        newUser.setEmail(userDTO.getEmail());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setPassword(userDTO.getPassword());

        User user =
        return responseService.getSingleResult(userRepository.save(newUser));
    }*/


    @DeleteMapping(value = "/users/{id}")
    public CommonResult delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return responseService.getSuccessResult();
    }

}
