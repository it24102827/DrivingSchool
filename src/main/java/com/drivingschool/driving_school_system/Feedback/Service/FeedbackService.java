package com.drivingschool.driving_school_system.Feedback.Service;

import com.drivingschool.driving_school_system.Feedback.Model.Feedback;
import com.drivingschool.driving_school_system.Feedback.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public Feedback addFeedback(String submittedByName, String submittedById, String feedbackText, int rating) {
        String feedbackId = UUID.randomUUID().toString();
        Feedback feedback = new Feedback(feedbackId, submittedByName, submittedById, feedbackText, rating);
        feedback.setSubmittedAt(LocalDateTime.now());
        feedbackRepository.save(feedback);
        return feedback;
    }

    public List<Feedback> getFeedbacksBySubmittedById(String submittedById) {
        return feedbackRepository.findBySubmittedById(submittedById);
    }

    public void updateFeedback(Feedback feedback) {
        feedback.setSubmittedAt(LocalDateTime.now()); 
        feedbackRepository.update(feedback);
    }

    public void deleteFeedbackById(String feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Optional<Feedback> getFeedbackById(String feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }
}
