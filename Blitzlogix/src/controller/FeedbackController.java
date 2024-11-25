package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import java.time.LocalDateTime;

public class FeedbackController {

    @FXML
    private TextField parcelIdField;

    @FXML
    private TextField writerIdField;

    @FXML
    private TextArea commentArea;

    @FXML
    private TextField scoreField;

    @FXML
    private Label statusLabel;

    @FXML
    public void submitFeedback() {
        String parcelIdText = parcelIdField.getText().trim();
        String writerIdText = writerIdField.getText().trim();
        String comment = commentArea.getText().trim();
        String scoreText = scoreField.getText().trim();

        // Validate Inputs
        if (parcelIdText.isEmpty() || writerIdText.isEmpty() || comment.isEmpty() || scoreText.isEmpty()) {
            statusLabel.setText("All fields are required.");
            

            return;
        }

        int parcelId;
        int writerId;
        int score;

        try {
            parcelId = Integer.parseInt(parcelIdText);
            writerId = Integer.parseInt(writerIdText);
            score = Integer.parseInt(scoreText);
        } catch (NumberFormatException e) {
            statusLabel.setText("Parcel ID, Writer ID, and Score must be valid numbers.");
            return;
        }

        if (score < 1 || score > 10) {
            statusLabel.setText("Score must be between 1 and 10.");
            return;
        }

        // Create Feedback Object
        Feedback feedback = new Feedback();
       
        feedback.setParcelID(parcelId);
        feedback.setWriterID(writerId);
        feedback.setComment(comment);
        feedback.setScore(score);
        feedback.setTimestamp(LocalDateTime.now());

        // Process Feedback (Simulate saving feedback)
        if (saveFeedback(feedback)) {
            statusLabel.setText("Feedback submitted successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");

            parcelIdField.clear();
            writerIdField.clear();
            commentArea.clear();
            scoreField.clear();
        } else {
            statusLabel.setText("Failed to submit feedback. Try again.");
        }
    }

    // Simulated database save operation
    private boolean saveFeedback(Feedback feedback) {
        System.out.println("Feedback Saved: " + feedback);
        // Add logic to save feedback to a database or file.
        return true; // Simulate success
    }
}
