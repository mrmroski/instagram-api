package com.instagram.instagram.controller;

import com.instagram.instagram.models.User;
import com.instagram.instagram.models.command.CreateUserCommand;
import com.instagram.instagram.models.dto.UserDto;
import com.instagram.instagram.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserCommand command) {
        User userToAdd = modelMapper.map(command, User.class);
        User createdUser = userService.createUser(userToAdd);
        return new ResponseEntity<>(modelMapper.map(createdUser, UserDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable("id") int id) {
        return new ResponseEntity(modelMapper.map((userService.findUserById(id)), UserDto.class), HttpStatus.OK);
    }

    @GetMapping("/findUser/{username}") //TODO: check if there is better way for that
    public ResponseEntity<UserDto> findUserByUsername(@PathVariable("username") String username){
        return new ResponseEntity<>(modelMapper.map((userService.findUserByUsername(username)), UserDto.class), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers() {
        return new ResponseEntity(userService.findAllUsers()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }


    //TODO @Valid not working?
}
