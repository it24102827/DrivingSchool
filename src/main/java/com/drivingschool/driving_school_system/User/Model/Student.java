package com.drivingschool.driving_school_system.User.Model;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Student extends User {
    private String licenseType;

    public Student(String id, String name, String email, String password, String licenseType) {
        super(id, name, email, password, "student");
        this.licenseType = licenseType;
    }

    public String getLicenseType() {
        return licenseType;
    }
}