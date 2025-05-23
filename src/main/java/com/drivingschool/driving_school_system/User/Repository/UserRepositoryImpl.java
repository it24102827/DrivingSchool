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
    private static final String USERS_FILE = "C:\\SLLIT\\Y1S2\\OOP\\DrivingSchool\\DrivingSchool\\driving-school-system\\driving-school-system\\src\\main\\resources\\data\\users.txt";

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

        }
        return users;
    }

    @Override
    public void update(User updatedUser) {
        List<User> users = findAll().stream()
                .map(user -> user.getEmail().equalsIgnoreCase(updatedUser.getEmail()) ? updatedUser : user)
                .collect(Collectors.toList());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(serializeUser(user));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByEmail(String email) {
        List<User> users = findAll().stream()
                .filter(user -> !user.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(serializeUser(user));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        return null;
    }

    @Override
    public List<User> findInstructors() {
        // Get all users and filter instructors
        List<User> instructors = findAll().stream()
                .filter(user -> "instructor".equalsIgnoreCase(user.getRole()))
                .collect(Collectors.toList());

        // Convert to array for bubble sort
        User[] instructorArray = instructors.toArray(new User[0]);
        int n = instructorArray.length;

        // Bubble sort based on experience years
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Instructor current = (Instructor) instructorArray[j];
                Instructor next = (Instructor) instructorArray[j + 1];

                if (current.getExperienceYears() < next.getExperienceYears()) {
                    // Swap elements
                    User temp = instructorArray[j];
                    instructorArray[j] = instructorArray[j + 1];
                    instructorArray[j + 1] = temp;
                }
            }
        }
        return Arrays.asList(instructorArray);
    }
}