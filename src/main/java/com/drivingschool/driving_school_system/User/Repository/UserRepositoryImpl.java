package com.drivingschool.driving_school_system.User.Repository;

import com.drivingschool.driving_school_system.User.Model.Instructor;
import com.drivingschool.driving_school_system.User.Model.Student;
import com.drivingschool.driving_school_system.User.Model.User;
import com.drivingschool.driving_school_system.User.Repository.UserRepository;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserRepositoryImpl implements UserRepository {
    private static final String USERS_FILE = "src/main/resources/data/users.txt";

    @Override
    public void save(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(serializeUser(user));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(deserializeUser(line));
            }
        } catch (IOException e) {
            // File might not exist yet
        }
        return users;
    }

    private String serializeUser(User user) {
        String base = String.join(",",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole());

        if (user instanceof Instructor) {
            return base + "," + ((Instructor) user).getExperienceYears();
        } else if (user instanceof Student) {
            return base + "," + ((Student) user).getLicenseType();
        }
        return base;
    }

    private User deserializeUser(String line) {
        String[] parts = line.split(",");
        String role = parts[4];

        if ("instructor".equalsIgnoreCase(role)) {
            return new Instructor(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[5]));
        } else if ("student".equalsIgnoreCase(role)) {
            return new Student(parts[0], parts[1], parts[2], parts[3], parts[5]);
        }
        return null; // Or handle other cases
    }
}