package com.drivingschool.driving_school_system.controller;

import com.drivingschool.driving_school_system.User.Model.Instructor;
import com.drivingschool.driving_school_system.User.Model.Student;
import com.drivingschool.driving_school_system.User.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("session", session); // Makes session.user accessible
        return "index";
    }


    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login";
        }

        // Explicitly cast to the correct type based on role
        if ("student".equals(user.getRole())) {
            Student student = (Student) user;
            model.addAttribute("licenseType", student.getLicenseType());
        } else if ("instructor".equals(user.getRole())) {
            Instructor instructor = (Instructor) user;
            model.addAttribute("experienceYears", instructor.getExperienceYears());
        }

        model.addAttribute("session", session);
        return "profile";
    }


}