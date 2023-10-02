package com.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "content")
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int content_id;

//	private String img;
	@Column
	private String content_desc;

	@Column
	private String content_title;

	@Column
	private String content_date;

	@Column
	private String content_topic;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "u_id", referencedColumnName = "id")
	private Account account;
	public Content() {
		super();
	}

	public Content(int content_id, String content_desc, String content_title, String content_date,
			String content_topic) {
		super();
		this.content_id = content_id;
		this.content_desc = content_desc;
		this.content_title = content_title;
		this.content_date = content_date;
		this.content_topic = content_topic;
	}

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public String getContent_desc() {
		return content_desc;
	}

	public void setContent_desc(String content_desc) {
		this.content_desc = content_desc;
	}

	public String getContent_title() {
		return content_title;
	}

	public void setContent_title(String content_title) {
		this.content_title = content_title;
	}

	public String getContent_date() {
		return content_date;
	}

	public void setContent_date(String content_date) {
		this.content_date = content_date;
	}

	public String getContent_topic() {
		return content_topic;
	}

	public void setContent_topic(String content_topic) {
		this.content_topic = content_topic;
	}

}
