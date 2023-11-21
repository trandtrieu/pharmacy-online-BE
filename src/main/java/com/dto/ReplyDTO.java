package com.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReplyDTO {
	private int reply_id;
	private String reply_feedback;
	private String created_at_time;
	private LocalDate created_date;
	private int feedback_id;
	private long user_id;
	private String user_name;
	private String avatar;
	public ReplyDTO() {
		super();
	}
	public ReplyDTO(int reply_id, String reply_feedback, LocalTime created_at_time, LocalDate created_date,
			int feedback_id, long user_id, String user_name) {
		super();
		this.reply_id = reply_id;
		this.reply_feedback = reply_feedback;
		this.created_at_time = created_at_time.format(DateTimeFormatter.ofPattern("HH:mm"));
		this.created_date = created_date;
		this.feedback_id = feedback_id;
		this.user_id = user_id;
		this.user_name = user_name;
	}
	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	public String getReply_feedback() {
		return reply_feedback;
	}
	public void setReply_feedback(String reply_feedback) {
		this.reply_feedback = reply_feedback;
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
	public int getFeedback_id() {
		return feedback_id;
	}
	public void setFeedback_id(int feedback_id) {
		this.feedback_id = feedback_id;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
	
}
