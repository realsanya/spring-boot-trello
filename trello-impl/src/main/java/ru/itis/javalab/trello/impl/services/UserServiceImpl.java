package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.impl.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService<UserDto, Long> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void signUp(UserDto userDto) {

    }
}
