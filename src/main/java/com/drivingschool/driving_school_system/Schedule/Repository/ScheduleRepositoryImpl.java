package com.drivingschool.driving_school_system.Schedule.Repository;

import com.drivingschool.driving_school_system.Schedule.Model.Schedule;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class ScheduleRepositoryImpl implements ScheduleRepository {
    private static final String SCHEDULE_FILE = "C:\\FreeLanceProjects\\DrivingSchool\\driving-school-system\\driving-school-system\\src\\main\\resources\\data\\schedules.txt";
    private final Queue<Schedule> scheduleQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void save(Schedule schedule) {
        scheduleQueue.add(schedule);
        processQueue();
    }

    private void processQueue() {
        while (!scheduleQueue.isEmpty()) {
            Schedule schedule = scheduleQueue.poll();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULE_FILE, true))) {
                writer.write(schedule.getId() + "," +
                        schedule.getLessonId() + "," +
                        schedule.getLessonName() + "," + 
                        schedule.getStudentId() + "," +
                        schedule.getInstructorName() + "," +
                        schedule.getScheduledAt() + "," +
                        schedule.getDurationMinutes() + "," +
                        schedule.getStatus());
                writer.newLine();
            } catch (IOException e) {
                scheduleQueue.add(schedule);
                throw new RuntimeException("Failed to save schedule", e);
            }
        }
    }

    @Override
    public void update(Schedule updatedSchedule) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(updatedSchedule.getId())) {
                    lines.add(updatedSchedule.getId() + "," +
                            updatedSchedule.getLessonId() + "," +
                            updatedSchedule.getLessonName() + "," + 
                            updatedSchedule.getStudentId() + "," +
                            updatedSchedule.getInstructorName() + "," +
                            updatedSchedule.getScheduledAt() + "," +
                            updatedSchedule.getDurationMinutes() + "," +
                            updatedSchedule.getStatus());
                    found = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to update schedule", e);
        }

        if (!found) {
            throw new RuntimeException("Schedule not found: " + updatedSchedule.getId());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULE_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write updated schedules", e);
        }
    }

    @Override
    public void deleteById(String id) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id)) {
                    found = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete schedule", e);
        }

        if (!found) {
            throw new RuntimeException("Schedule not found: " + id);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCHEDULE_FILE))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write updated schedules", e);
        }
    }

    @Override
    public List<Schedule> findByInstructorName(String instructorName) {
        return findAll().stream()
                .filter(s -> s.getInstructorName().equals(instructorName))
                .toList();
    }

    @Override
    public List<Schedule> findByStudentId(String studentId) {
        return findAll().stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .toList();
    }

    @Override
    public List<Schedule> findByStatus(String status) {
        return findAll().stream()
                .filter(s -> s.getStatus().equals(status))
                .toList();
    }

    @Override
    public List<Schedule> findAll() {
        List<Schedule> schedules = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(SCHEDULE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) { 
                    schedules.add(new Schedule(
                            parts[0], 
                            parts[1], 
                            parts[2], 
                            parts[3], 
                            parts[4], 
                            LocalDateTime.parse(parts[5]), 
                            Integer.parseInt(parts[6]),
                            parts[7] 
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read schedules", e);
        }

        return schedules;
    }
}