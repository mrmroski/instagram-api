package com.instagram.instagram.service;

import com.instagram.instagram.exception.ResourceNotFoundException;
import com.instagram.instagram.models.User;
import com.instagram.instagram.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
       return userRepository.findAll();
    }

    public User createUser(User user){
        user.setCreatedAt(LocalDate.now());
        return userRepository.save(user);
    }

    public User findUserById(int id){
        return userRepository.findById(id) //TODO: write an exception
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User with id %s not found!", id)));
    }

    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("Customer with username " + username + " not found"));
    }

    public void deleteUserById(int id){
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(String.format("User with id %s not found!", id));
        }
    }


    //TODO: write editUser & editUserPartially
    //TODO: write soft delete


}
