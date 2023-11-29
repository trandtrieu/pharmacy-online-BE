package com.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reply")
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reply_id;
	
	@Column(columnDefinition = "nvarchar(max)")
	private String reply_feedback;
	
	@Column(name = "created_at")
    private LocalTime created_at_time;
    
    @Column(name = "created_date")
    private LocalDate created_date;
    
    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

	public Reply() {
		super();
	}

	public Reply(int reply_id, String reply_feedback, LocalTime created_at_time, LocalDate created_date,
			Feedback feedback, Account account) {
		super();
		this.reply_id = reply_id;
		this.reply_feedback = reply_feedback;
		this.created_at_time = created_at_time;
		this.created_date = created_date;
		this.feedback = feedback;
		this.account = account;
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

	public LocalTime getCreated_at_time() {
		return created_at_time;
	}

	public void setCreated_at_time(LocalTime created_at_time) {
		this.created_at_time = created_at_time;
	}

	public LocalDate getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDate created_date) {
		this.created_date = created_date;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
    
    
}
