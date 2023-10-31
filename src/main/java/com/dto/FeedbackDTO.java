package com.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
	private int feedback_id;
	private int rating;
	private String comment;
	private String created_at_time;
	private LocalDate created_date;
	private int product_id;
	private String p_name;
	private long user_id;
	private String user_name;
//	private List<Reply> reply;

	
	public FeedbackDTO(int feedback_id, int rating, String comment, LocalTime created_at_time, LocalDate created_date,
		 int product_id, String p_name, long user_id, String user_name) {
	super();
	this.feedback_id = feedback_id;
	this.rating = rating;
	this.comment = comment;
	this.created_at_time = created_at_time.format(DateTimeFormatter.ofPattern("HH:mm"));
	this.created_date = created_date;
	this.product_id = product_id;
	this.p_name = p_name;
	this.user_id = user_id;
	this.user_name = user_name;
}

	
	

	public int getFeedback_id() {
		return feedback_id;
	}

	public void setFeedback_id(int feedback_id) {
		this.feedback_id = feedback_id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreated_at_time() {
		return created_at_time;
	}

	public void setCreated_at_time(String created_at_time) {
		this.created_at_time = created_at_time;
	}

	public LocalDate getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}


	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	
	
	
	
	
}
