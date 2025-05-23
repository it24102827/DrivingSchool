package com.drivingschool.driving_school_system.Admin.Controller.Repository;

import com.drivingschool.driving_school_system.Admin.Model.Admin;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminRepositoryImpl implements AdminRepository {

    private static final String ADMIN_FILE = "C:\\FreeLanceProjects\\DrivingSchool\\driving-school-system\\driving-school-system\\src\\main\\resources\\data\\admins.txt";

    @Override
    public void save(Admin admin) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE, true))) {
            String line = String.format("%s,%s,%s,%s",
                    admin.getId(),
                    admin.getName(),
                    admin.getEmail(),
                    admin.getPassword());

            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save admin", e);
        }
    }

    @Override
    public void update(Admin updatedAdmin) {
        List<String> updatedLines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4); 

                if (parts.length == 4 && parts[0].equals(updatedAdmin.getId())) {
                    String newLine = String.format("%s,%s,%s,%s",
                            updatedAdmin.getId(),
                            updatedAdmin.getName(),
                            updatedAdmin.getEmail(),
                            updatedAdmin.getPassword());
                    updatedLines.add(newLine);
                    updated = true;
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read admin file for update", e);
        }

        if (!updated) {
            throw new RuntimeException("Admin with ID " + updatedAdmin.getId() + " not found.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write updated admin file", e);
        }
    }

    @Override
    public void deleteById(String id) {
        List<String> remainingLines = new ArrayList<>();
        boolean deleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4); 

                if (parts.length == 4 && !parts[0].equals(id)) {
                    remainingLines.add(line);
                } else if (parts.length == 4 && parts[0].equals(id)) {
                    deleted = true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read admin file for deletion", e);
        }

        if (!deleted) {
            throw new RuntimeException("Admin with ID " + id + " not found.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (String remainingLine : remainingLines) {
                writer.write(remainingLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write updated admin file after deletion", e);
        }
    }

    @Override
    public Admin findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);

                if (parts.length == 4 && parts[0].equals(id)) {
                    return new Admin(parts[0], parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read admin file", e);
        }

        return null;
    }

    @Override
    public Admin findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);

                if (parts.length == 4 && parts[2].equals(email)) {
                    return new Admin(parts[0], parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read admin file", e);
        }

        return null;
    }

    @Override
    public List<Admin> findAll() {
        List<Admin> admins = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);

                if (parts.length == 4) {
                    Admin admin = new Admin(parts[0], parts[1], parts[2], parts[3]);
                    admins.add(admin);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read admin file", e);
        }

        return admins;
    }
}
