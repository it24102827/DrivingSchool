package com.drivingschool.driving_school_system.Feedback.Repository;

import com.drivingschool.driving_school_system.Feedback.Model.Feedback;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FeedbackRepositoryImpl implements FeedbackRepository {
    private static final String FEEDBACK_FILE = "C:\\Users\\USER\\Downloads\\DrivingSchool correct\\DrivingSchool\\driving-school-system\\driving-school-system\\src\\main\\resources\\data\\feedback.txt";

    @Override
    public void save(Feedback feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE, true))) {
            String line = String.format("%s,%s,%s,%s,%s,%d",
                    feedback.getId(),
                    feedback.getSubmittedByName(),
                    feedback.getSubmittedById(),
                    feedback.getFeedbackText(),
                    feedback.getSubmittedAt().toString(),
                    feedback.getRating());

            writer.write(line);
            writer.newLine();

        } catch (Exception e) {
            throw new RuntimeException("Failed to save feedback", e);
        }
    }

    @Override
    public void update(Feedback updatedFeedback) {
        List<String> updatedLines = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);

                if (parts.length == 6 && parts[0].equals(updatedFeedback.getId())) {
                    // Replace this line with updated feedback
                    String newLine = String.format("%s,%s,%s,%s,%s,%d",
                            updatedFeedback.getId(),
                            updatedFeedback.getSubmittedByName(),
                            updatedFeedback.getSubmittedById(),
                            updatedFeedback.getFeedbackText(),
                            updatedFeedback.getSubmittedAt().toString(),
                            updatedFeedback.getRating());

                    updatedLines.add(newLine);
                    updated = true;
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read feedback file for update", e);
        }

        if (!updated) {
            throw new RuntimeException("Feedback with ID " + updatedFeedback.getId() + " not found.");
        }

        // Write all lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE))) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write updated feedback file", e);
        }
    }

    @Override
    public void deleteById(String id) {
        List<String> remainingLines = new ArrayList<>();
        boolean deleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);

                if (parts.length == 6 && !parts[0].equals(id)) {
                    // Keep the feedback if the ID doesn't match
                    remainingLines.add(line);
                } else if (parts.length == 6 && parts[0].equals(id)) {
                    // Mark as deleted if the ID matches
                    deleted = true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read feedback file for deletion", e);
        }

        if (!deleted) {
            throw new RuntimeException("Feedback with ID " + id + " not found.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE))) {
            for (String remainingLine : remainingLines) {
                writer.write(remainingLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write updated feedback file after deletion", e);
        }
    }

    @Override
    public List<Feedback> findBySubmittedById(String id) {
        List<Feedback> feedbackList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6); // limit to avoid splitting feedback text with commas

                if (parts.length == 6) {
                    String feedbackId = parts[0];
                    String submittedByName = parts[1];
                    String submittedById = parts[2];
                    String feedbackText = parts[3];
                    String submittedAtStr = parts[4];
                    int rating = Integer.parseInt(parts[5]);

                    if (submittedById.equals(id)) {
                        Feedback feedback = new Feedback(feedbackId, submittedByName, submittedById, feedbackText,
                                rating);
                        feedback.setSubmittedAt(LocalDateTime.parse(submittedAtStr));
                        feedbackList.add(feedback);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read feedback file", e);
        }

        return feedbackList;
    }

    @Override
    public List<Feedback> findAll() {
        return List.of();
    }

    @Override
    public Optional<Feedback> findById(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FEEDBACK_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6); // limit to avoid splitting feedback text with commas

                if (parts.length == 6 && parts[0].equals(id)) {
                    String feedbackId = parts[0];
                    String submittedByName = parts[1];
                    String submittedById = parts[2];
                    String feedbackText = parts[3];
                    String submittedAtStr = parts[4];
                    int rating = Integer.parseInt(parts[5]);

                    Feedback feedback = new Feedback(feedbackId, submittedByName, submittedById, feedbackText, rating);
                    feedback.setSubmittedAt(LocalDateTime.parse(submittedAtStr));
                    return Optional.of(feedback);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read feedback file", e);
        }

        return Optional.empty();
    }
}
