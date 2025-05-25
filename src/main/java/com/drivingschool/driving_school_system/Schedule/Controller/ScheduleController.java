package com.drivingschool.driving_school_system.Schedule.Controller;

import com.drivingschool.driving_school_system.Lesson.Model.Lesson;
import com.drivingschool.driving_school_system.Lesson.Service.LessonService;
import com.drivingschool.driving_school_system.Schedule.Model.Schedule;
import com.drivingschool.driving_school_system.Schedule.Service.ScheduleService;
import com.drivingschool.driving_school_system.User.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private LessonService lessonService;

    // Book a lesson - creates a new schedule
    @PostMapping("/book")
    public ResponseEntity<String> bookLesson(
            @RequestParam String lessonId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledAt,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null || !"student".equals(user.getRole())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Lesson lesson = lessonService.getAllLessons().stream()
                    .filter(l -> l.getId().equals(lessonId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));

            Schedule schedule = scheduleService.createSchedule(
                    lesson.getId(),
                    lesson.getLessonName(), 
                    user.getId(),
                    lesson.getInstructorName(),
                    scheduledAt,
                    lesson.getDuration());

            return ResponseEntity.ok("Lesson booked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get schedules for current user (student or instructor)
    @GetMapping("/my")
    public String getUserSchedules(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login";
        }

        if ("student".equals(user.getRole())) {
            model.addAttribute("schedules", scheduleService.getSchedulesByStudent(user.getId()));
        } else if ("instructor".equals(user.getRole())) {
            model.addAttribute("schedules", scheduleService.getSchedulesByInstructor(user.getName()));
        }

        return "schedule-list";
    }

    // Cancel a schedule
    @PostMapping("/{id}/cancel")
    public String cancelSchedule(
            @PathVariable String id,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/users/login";
        }

        try {
            scheduleService.cancelSchedule(id);
            return "redirect:/schedules/my";
        } catch (Exception e) {

            return "redirect:/schedules/my";
        }
    }

    // Complete a schedule (for instructors)
    @PostMapping("/{id}/complete")
    public ResponseEntity<String> completeSchedule(
            @PathVariable String id,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null || !"instructor".equals(user.getRole())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            scheduleService.completeSchedule(id);
            return ResponseEntity.ok("Schedule marked as completed");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get schedule details
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleDetails(
            @PathVariable String id,
            HttpSession session) {

        if (session.getAttribute("user") == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            Schedule schedule = scheduleService.getScheduleById(id);
            return ResponseEntity.ok(schedule);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<String> editScheduleTime(
            @PathVariable String id,
            @RequestParam("newScheduledAt") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newScheduledAt,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        try {
            Schedule schedule = scheduleService.getScheduleById(id);
            schedule.setScheduledAt(newScheduledAt);
            scheduleService.updateSchedule(schedule);
            return ResponseEntity.ok("Schedule time updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete a schedule
    @GetMapping("/{id}/delete")
    public String deleteSchedule(
            @PathVariable String id,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null && session.getAttribute("admin") == null) {
            return "redirect:/users/login";
        }

        try {
            scheduleService.deleteSchedule(id);
            return "redirect:/schedules/my";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete schedule with ID " + id);
            return "redirect:/schedules/my?error";
        }
    }
}