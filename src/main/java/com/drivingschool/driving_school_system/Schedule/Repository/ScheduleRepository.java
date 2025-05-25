package com.drivingschool.driving_school_system.Schedule.Repository;

import com.drivingschool.driving_school_system.Schedule.Model.Schedule;
import java.util.List;

public interface ScheduleRepository {
    void save(Schedule schedule);

    void update(Schedule schedule);

    void deleteById(String id);

    List<Schedule> findByInstructorName(String instructorName);

    List<Schedule> findByStudentId(String studentId);

    List<Schedule> findByStatus(String status);

    List<Schedule> findAll();
}