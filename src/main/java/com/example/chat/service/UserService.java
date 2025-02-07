package com.example.chat.service;

import com.example.chat.Entity.Message;
import com.example.chat.Entity.User;
import com.example.chat.Exception.MessageNotFoundException;
import com.example.chat.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    public Optional<User> getUserById(Long id) {
            return userRepository.findById(id);
    }
}
