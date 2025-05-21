package com.drivingschool.driving_school_system.User.Repository;

import com.drivingschool.driving_school_system.User.Model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    void update(User user);

    void deleteByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    List<User> findInstructors();

}