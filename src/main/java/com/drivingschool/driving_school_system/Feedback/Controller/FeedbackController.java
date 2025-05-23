package com.drivingschool.driving_school_system.Feedback.Controller;

import com.drivingschool.driving_school_system.Feedback.Model.Feedback;
import com.drivingschool.driving_school_system.Feedback.Service.FeedbackService;
import com.drivingschool.driving_school_system.User.Model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/add")
    public String addFeedback(@RequestParam String submittedByName,
            @RequestParam String submittedById,
            @RequestParam String feedbackText,
            @RequestParam int rating,
            HttpSession session,
            Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/users/login";
        }

        feedbackService.addFeedback(submittedByName, submittedById, feedbackText, rating);
        return "redirect:/feedback?success";
    }

    @GetMapping("/my")
    public String getUserFeedbacks(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/users/login";
        }

        User currentUser = (User) session.getAttribute("user");
        List<Feedback> feedbacks = feedbackService.getFeedbacksBySubmittedById(currentUser.getId());
        model.addAttribute("feedbacks", feedbacks);

        return "user-feedbacks";
    }

    @GetMapping("/update/{id}")
    public String showUpdateFeedbackForm(@PathVariable("id") String id, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/users/login";
        }

        Feedback feedback = feedbackService.getFeedbackById(id).stream()
                .findFirst().orElse(null);
        if (feedback == null) {
            return "redirect:/feedback?notFound";
        }

        model.addAttribute("feedback", feedback);
        return "update-feedback";
    }

    @PostMapping("/update")
    public String updateFeedback(@RequestParam String feedbackId,
            @RequestParam String submittedByName,
            @RequestParam String submittedById,
            @RequestParam String feedbackText,
            @RequestParam int rating,
            HttpSession session,
            Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/users/login";
        }

        Feedback feedback = new Feedback(feedbackId, submittedByName, submittedById, feedbackText, rating);
        feedbackService.updateFeedback(feedback);
        return "redirect:/feedback/my?updated";
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable("id") String id, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/users/login";
        }

        try {
            feedbackService.deleteFeedbackById(id);
            return "redirect:/feedback/my?deleted";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete feedback with ID " + id);
            return "redirect:/feedback?error";
        }
    }
}
