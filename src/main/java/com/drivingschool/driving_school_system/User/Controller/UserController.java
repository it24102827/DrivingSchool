package com.drivingschool.driving_school_system.User.Controller;


import com.drivingschool.driving_school_system.User.Model.User;
import com.drivingschool.driving_school_system.User.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "auth/register";
    }

    @PostMapping("/register/instructor")
    public String registerInstructor(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int experienceYears,
            HttpSession session) {
        User user = userService.registerUser("instructor", name, email, password, experienceYears);
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @PostMapping("/register/student")
    public String registerStudent(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String licenseType,
            HttpSession session) {
        User user = userService.registerUser("student", name, email, password, licenseType);
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {
        User user = userService.validateUser(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/login?error";
    }
}