package com.drivingschool.driving_school_system.Admin.Controller.Service;

import com.drivingschool.driving_school_system.Admin.Model.Admin;
import com.drivingschool.driving_school_system.Admin.Repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin addAdmin(String name, String email, String password) {
        String id = UUID.randomUUID().toString();
        Admin admin = new Admin(id, name, email, password);
        adminRepository.save(admin);
        return admin;
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin getAdminById(String id) {
        return adminRepository.findById(id);
    }

    public void updateAdmin(Admin admin) {
        adminRepository.update(admin);
    }

    public void deleteAdminById(String id) {
        adminRepository.deleteById(id);
    }

    public Admin login(String email, String password) {
        try {
            Admin admin = adminRepository.findByEmail(email);

            if (admin != null && admin.getPassword().equals(hashPassword(password))) {
                return admin;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.err.println("An error occurred during admin login: " + e.getMessage());
            return null;
        }
    }


    private String hashPassword(String plainText) {
        return plainText;
    }

}
