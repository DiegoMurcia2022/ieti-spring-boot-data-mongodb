package org.ada.school.service.impl;

import org.ada.school.dto.UserDto;
import org.ada.school.model.User;
import org.ada.school.respository.UserDocument;
import org.ada.school.respository.UserRepository;
import org.ada.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceMongoDB implements UserService {
    private final UserRepository userRepository;

    public UserServiceMongoDB(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        userRepository.save(new UserDocument(user.getId(), user.getName(), user.getEmail(), user.getLastName(), user.getCreatedAt()));
        return user;
    }

    @Override
    public User findById(String id) {
        UserDocument userDocument = userRepository.findById(id).get();

        return new User(userDocument.getId(), userDocument.getName(), userDocument.getEmail(), userDocument.getLastName(), userDocument.getCreatedAt());
    }

    @Override
    public List<User> all() {
        List<UserDocument> userDocumentList = userRepository.findAll();
        List<User> userList = new ArrayList<>();
        userDocumentList.forEach(userDocument -> {userList.add(new User(userDocument.getId(), userDocument.getName(), userDocument.getEmail(), userDocument.getLastName(), userDocument.getCreatedAt()));});

        return userList;
    }

    @Override
    public boolean deleteById(String id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public User update(UserDto userDto, String id) {
        UserDocument userDocument = userRepository.findById(id).get();

        userRepository.deleteById(id);

        userDocument.setName(userDto.getName());
        userDocument.setEmail(userDto.getEmail());
        userDocument.setLastName(userDto.getLastName());

        userRepository.save(userDocument);

        return new User(userDocument.getId(), userDocument.getName(), userDocument.getEmail(), userDocument.getLastName(), userDocument.getCreatedAt());
    }
}
