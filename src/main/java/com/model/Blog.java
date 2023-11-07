package com.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "blog")
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int blog_id;

	@Column(name = "title")
	private String title;

	@Column(name = "author_id")
	private int author_id;

	@Column
	private LocalDate create_date;
	
	@Column
	private LocalTime create_time;
	
	@Column
	private LocalDate update_date;
	
	@Column
	private LocalTime update_time;

	@Column(name = "content", columnDefinition = "NVARCHAR(max)")	
	private String content;

	@Column(name = "thumbnail")
	private String thumbnail;

	@Column(name = "categoryBlog_id")
	private int categoryBlog_id;

	@Column(name = "status")
	private String status;

	@ManyToMany(mappedBy = "blog", cascade = CascadeType.ALL)
	private List<Blog_img> img = new ArrayList<>();

}
