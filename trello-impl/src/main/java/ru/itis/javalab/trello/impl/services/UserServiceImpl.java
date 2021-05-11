package ru.itis.javalab.trello.impl.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.javalab.trello.api.dto.UserDto;
import ru.itis.javalab.trello.api.services.UserService;
import ru.itis.javalab.trello.impl.aspect.Caching;
import ru.itis.javalab.trello.impl.aspect.Logging;
import ru.itis.javalab.trello.impl.models.User;
import ru.itis.javalab.trello.impl.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

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
    public Optional<UserDto> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).map(
                user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    @Logging
    @Caching
    public Optional<UserDto> getUserById(Long userId) {
        return userRepository.findById(userId).map(
                user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return null;
    }

    @Override
    public void save(UserDto userDto) {
        userRepository.save(modelMapper.map(userDto, User.class));
    }

    @Override
    public void signUpAfterOAuth(String email, String name,  String provider) {
        User user = User.builder()
                .email(email)
                .name(name)
                .auth_provider(User.AuthProvider.GOOGLE)
                .role(User.Role.USER)
                .build();
        userRepository.save(user);
    }

    @Override
    public void updateUserAfterOAuth(UserDto userDto, String name, String provider) {
        User user = modelMapper.map(userDto, User.class);
        if (provider.equals(User.AuthProvider.GOOGLE.toString())) {
            user.setAuth_provider(User.AuthProvider.GOOGLE);
        } else {
            user.setAuth_provider(User.AuthProvider.LOCAL);
        }
        user.setName(name);
        userRepository.save(user);
    }
}
