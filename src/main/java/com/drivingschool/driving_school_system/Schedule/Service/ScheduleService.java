package com.drivingschool.driving_school_system.Schedule.Service;

import com.drivingschool.driving_school_system.Schedule.Model.Schedule;
import com.drivingschool.driving_school_system.Schedule.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule createSchedule(String lessonId, String lessonName, String studentId,
            String instructorName, LocalDateTime scheduledAt,
            int durationMinutes) {
        String scheduleId = UUID.randomUUID().toString();
        Schedule schedule = new Schedule(
                scheduleId,
                lessonId,
                lessonName, 
                studentId,
                instructorName,
                scheduledAt,
                durationMinutes,
                "Scheduled" 
        );
        scheduleRepository.save(schedule);
        return schedule;
    }

    public List<Schedule> getSchedulesByInstructor(String instructorName) {
        return scheduleRepository.findByInstructorName(instructorName);
    }

    public List<Schedule> getSchedulesByStudent(String studentId) {
        return scheduleRepository.findByStudentId(studentId);
    }

    public List<Schedule> getSchedulesByStatus(String status) {
        return scheduleRepository.findByStatus(status);
    }

    public void updateSchedule(Schedule schedule) {
        // Ensure status is valid
        if (!isValidStatus(schedule.getStatus())) {
            throw new IllegalArgumentException("Invalid schedule status");
        }

        // Ensure the new scheduled time is in the future
        if (schedule.getScheduledAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Schedule time must be in the future");
        }

        scheduleRepository.update(schedule);
    }

    public void cancelSchedule(String scheduleId) {
        Schedule schedule = getScheduleById(scheduleId);
        schedule.setStatus("Cancelled");
        scheduleRepository.update(schedule);
    }

    public void completeSchedule(String scheduleId) {
        Schedule schedule = getScheduleById(scheduleId);
        schedule.setStatus("Completed");
        scheduleRepository.update(schedule);
    }

    public void deleteSchedule(String scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(String scheduleId) {
        return scheduleRepository.findAll().stream()
                .filter(s -> s.getId().equals(scheduleId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    private boolean isValidStatus(String status) {
        return status.equals("Scheduled") ||
                status.equals("Completed") ||
                status.equals("Cancelled");
    }
}