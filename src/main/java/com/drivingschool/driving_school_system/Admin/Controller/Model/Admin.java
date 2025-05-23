package com.drivingschool.driving_school_system.Admin.Controller.Model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Admin {

    private String id;
    private String name;
    private String email;
    private String password;

    public Admin(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
