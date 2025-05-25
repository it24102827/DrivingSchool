package com.drivingschool.driving_school_system.Schedule.Model;

import java.time.LocalDateTime;

public class Schedule {
    private String id;
    private String lessonId;
    private String lessonName; // New field
    private String studentId;
    private String instructorName;
    private LocalDateTime scheduledAt;
    private int durationMinutes;
    private String status;

    public Schedule(String id, String lessonId, String lessonName, String studentId,
            String instructorName, LocalDateTime scheduledAt,
            int durationMinutes, String status) {
        this.id = id;
        this.lessonId = lessonId;
        this.lessonName = lessonName; // Initialize new field
        this.studentId = studentId;
        this.instructorName = instructorName;
        this.scheduledAt = scheduledAt;
        this.durationMinutes = durationMinutes;
        this.status = status;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getLessonName() { // New getter
        return lessonName;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setLessonName(String lessonName) { // New setter
        this.lessonName = lessonName;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}