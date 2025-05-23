package com.drivingschool.driving_school_system.User.Service;

import com.drivingschool.driving_school_system.User.Factory.UserFactory;
import com.drivingschool.driving_school_system.User.Model.User;
import com.drivingschool.driving_school_system.User.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String role, String name, String email, String password, Object roleSpecificData) {
        String userId = UUID.randomUUID().toString();
        User user = UserFactory.createUser(role, userId, name, email, password, roleSpecificData);
        userRepository.save(user);
        return user;
    }

    public void updateUser(User updatedUser) {
        userRepository.update(updatedUser);
    }

    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    public User validateUser(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(hashPassword(password)))
                .orElse(null);
    }

    public List<User> findInstructorsSortedByExperience() {
        return userRepository.findInstructors();
    }

    private String hashPassword(String plainText) {
       
        return plainText; 
    }
}