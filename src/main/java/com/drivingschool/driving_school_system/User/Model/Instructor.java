package com.drivingschool.driving_school_system.User.Model;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Instructor extends User {
    private int experienceYears;

    public Instructor(String id, String name, String email, String password, int experienceYears) {
        super(id, name, email, password, "instructor");
        this.experienceYears = experienceYears;
    }

    public int getExperienceYears() {
        return experienceYears;
    }
}