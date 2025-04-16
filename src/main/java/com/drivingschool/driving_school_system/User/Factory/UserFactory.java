package com.drivingschool.driving_school_system.User.Factory;


import com.drivingschool.driving_school_system.User.Model.Instructor;
import com.drivingschool.driving_school_system.User.Model.Student;
import com.drivingschool.driving_school_system.User.Model.User;

public class UserFactory {
    public static User createUser(String role, String id, String name, String email, String password, Object roleSpecificData) {
        return switch (role.toLowerCase()) {
            case "instructor" -> new Instructor(id, name, email, password, (Integer) roleSpecificData);
            case "student" -> new Student(id, name, email, password, (String) roleSpecificData);
            default -> throw new IllegalArgumentException("Invalid user role: " + role);
        };
    }
}