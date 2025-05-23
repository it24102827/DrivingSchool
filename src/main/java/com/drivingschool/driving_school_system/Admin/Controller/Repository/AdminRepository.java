package com.drivingschool.driving_school_system.Admin.Controller.Repository;

import com.drivingschool.driving_school_system.Admin.Model.Admin;

import java.util.List;

public interface AdminRepository {

    void save(Admin admin);
    void update(Admin admin);
    void deleteById(String id);

    Admin findById(String id);
    Admin findByEmail(String email);
    List<Admin> findAll();
}
