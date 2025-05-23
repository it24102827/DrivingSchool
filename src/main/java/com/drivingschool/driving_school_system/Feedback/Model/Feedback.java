package com.drivingschool.driving_school_system.Feedback.Model;

import java.time.LocalDateTime;

public class Feedback {
    private String id;
    private String submittedByName;
    private String submittedById;

    private String feedbackText;
    private LocalDateTime submittedAt;
    private int rating;

    public Feedback(String id, String submittedByName,String submittedById, String feedbackText, int rating) {
        this.id = id;
        this.submittedByName = submittedByName;
        this.submittedById = submittedById;
        this.feedbackText = feedbackText;
        this.submittedAt = LocalDateTime.now();
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getSubmittedByName() {
        return submittedByName;
    }

    public String getSubmittedById() {
        return submittedById;
    }


    public String getFeedbackText() {
        return feedbackText;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public int getRating() {
        return rating;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setSubmittedByName(String submittedByName) {
        this.submittedByName = submittedByName;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setSubmittedById(String submittedById) {
        this.submittedById = submittedById;
    }
}
