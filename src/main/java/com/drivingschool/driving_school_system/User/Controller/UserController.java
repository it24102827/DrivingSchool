package com.drivingschool.driving_school_system.User.Controller;

import com.drivingschool.driving_school_system.User.Factory.UserFactory;
import com.drivingschool.driving_school_system.User.Model.User;
import com.drivingschool.driving_school_system.User.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
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
            HttpSession session,
            Model model) {

        User user = userService.validateUser(email, password);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        }

        model.addAttribute("loginError", "Invalid email or password. Please try again.");
        return "auth/login";
    }

    @PostMapping("/update")
    public String updateUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) Integer experienceYears,
            @RequestParam(required = false) String licenseType,
            HttpSession session) {

        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null)
            return "redirect:/login";

        // Preserve existing password if new one isn't provided
        String updatedPassword = (password == null || password.isEmpty())
                ? currentUser.getPassword()
                : password;

        // Handle role-specific data
        Object roleSpecificData = currentUser.getRole().equals("instructor")
                ? experienceYears
                : licenseType;

        User updatedUser = UserFactory.createUser(
                currentUser.getRole(),
                currentUser.getId(),
                name,
                email,
                updatedPassword,
                roleSpecificData);

        userService.updateUser(updatedUser);
        session.setAttribute("user", updatedUser);

        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String email, HttpSession session) {
        userService.deleteUser(email);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();
        return "redirect:login";
    }

    @GetMapping("/instructors")
    public String getInstructorsSortedByExperience(Model model) {
        List<User> instructors = userService.findInstructorsSortedByExperience();
        model.addAttribute("instructors", instructors);
        return "instructors/list";
    }
}