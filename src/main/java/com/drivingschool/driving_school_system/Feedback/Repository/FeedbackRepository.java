package com.drivingschool.driving_school_system.Feedback.Repository;



import com.drivingschool.driving_school_system.Feedback.Model.Feedback;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository {

    void save(Feedback feedback);
    void update(Feedback feedback);
    void deleteById(String id);

    List<Feedback> findBySubmittedById(String id);
    List<Feedback> findAll();
    Optional<Feedback> findById(String id);
}
