Modelpackage com.drivingschool.driving_school_system.Admin.Controller;

import com.drivingschool.driving_school_system.Admin.Model.Admin;
import com.drivingschool.driving_school_system.Admin.Service.AdminService;
import com.drivingschool.driving_school_system.Lesson.Model.Lesson;
import com.drivingschool.driving_school_system.Lesson.Service.LessonService;
import com.drivingschool.driving_school_system.User.Model.User;
import com.drivingschool.driving_school_system.User.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listAdmins(Model model, HttpSession session) {
        if (session.getAttribute("admin") == null) {
            System.out.println("Admin session is null, redirecting to login");
            return "redirect:/admins/login";
        }

        List<Admin> admins = adminService.getAllAdmins();

        // Add console logs
        System.out.println("Retrieved admins: " + admins);
        System.out.println("Admins list size: " + (admins != null ? admins.size() : "null"));

        model.addAttribute("admins", admins);
        System.out.println("Added admins to model, returning admin-list view");
        return "admin/admin-list";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "admin/admin-register";
    }

    @PostMapping("/register")
    public String registerAdmin(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        adminService.addAdmin(name, email, password);
        redirectAttributes.addFlashAttribute("successMessage", "Admin successfully created!");
        return "redirect:/admins/dashboard";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "admin/admin-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        Admin admin = adminService.login(email, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admins/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "admin/admin-login"; 
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        if (session.getAttribute("admin") == null) {
            return "redirect:/admins/login";
        }

        // Add all data needed for all sections
        List<Admin> admins = adminService.getAllAdmins();
        model.addAttribute("admins", admins);

        // Add instructors sorted by experience
        List<User> instructors = userService.findInstructorsSortedByExperience();
        model.addAttribute("instructors", instructors);

        // Add lessons list
        List<Lesson> lessons = lessonService.getAllLessons();
        model.addAttribute("lessons", lessons);

        return "admin/admin-dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admins/login";
    }

    // @GetMapping("/edit/{id}")
    // public String showEditForm(@PathVariable("id") String id, Model model) {
    // Admin admin = adminService.getAdminById(id);
    // if (admin == null) {
    // return "redirect:/admins?notFound";
    // }
    // model.addAttribute("admin", admin);
    // return "admin-edit";
    // }

    @PostMapping("/update")
    public String updateAdmin(@ModelAttribute Admin admin) {
        adminService.updateAdmin(admin);
        return "redirect:/admins/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        adminService.deleteAdminById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Admin successfully deleted!");
        return "redirect:/admins/dashboard";
    }
}
