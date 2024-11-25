package model;

import java.time.LocalDateTime;

public class Feedback {
	 private int feedback_id,parcelID; //related to the delivery
	 private int writerID; //has to be the sender or reciever of the parcel
     //regardles reciever or sender(but has to be one of them or cannot give feedback)
	 private int score; //1-10
     //we can get sender and reciever ids from parcelID
	 private String comment;
	 private LocalDateTime timestamp;  
     //can be given at anytime after creation
     //late pickup, late dleiveyr, aur das masla ha 
     
     public Feedback(int parcelID, int writerID, int score, String comment) {
         this.parcelID = parcelID;
         this.writerID = writerID;
         this.score = score;
         this.comment = comment;
         this.timestamp = LocalDateTime.now(); // Assigns current timestamp
     }
     
     public Feedback(int parcelID, int writerID, int score, String comment, LocalDateTime timestamp) {
         this.parcelID = parcelID;
         this.writerID = writerID;
         this.score = score;
         this.comment = comment;
         this.timestamp = timestamp;
     }
     
     
     
     public Feedback() {
		// TODO Auto-generated constructor stub
	}

	public int getFeedbackID() {
         return feedback_id;
     }

     public int getParcelID() {
         return parcelID;
     }

     public int getWriterID() {
         return writerID;
     }

     public int getScore() {
         return score;
     }

     public String getComment() {  
         return comment;
     }

     public LocalDateTime getTimestamp() {
         return timestamp;
     }


     public void setFeedbackID(int feedbackID) {
         this.feedback_id = feedbackID;
     }

     public void setParcelID(int parcelID) {
         this.parcelID = parcelID;
     }

     public void setWriterID(int writerID) {
         this.writerID = writerID;
     }

     public void setScore(int score) {
         this.score = score;
     }

     public void setComment(String comment) {
    	 this.comment = comment;
     }

     public void setTimestamp(LocalDateTime timestamp) {
         this.timestamp = timestamp;

     }
     
     
}
