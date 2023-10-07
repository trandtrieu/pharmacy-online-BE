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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
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

}
