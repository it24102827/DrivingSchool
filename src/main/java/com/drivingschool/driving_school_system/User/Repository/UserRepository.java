package com.drivingschool.driving_school_system.User.Repository;


import com.drivingschool.driving_school_system.User.Model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    // Add other methods as needed
}