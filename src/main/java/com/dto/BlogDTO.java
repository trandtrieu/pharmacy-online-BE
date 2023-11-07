package com.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BlogDTO {
	private int blog_id;
	private String title;
	private int author_id;	
	private LocalDate create_date;
	private String create_time;
	private LocalDate update_date;
	private String update_time;
	private String content;
	private String thumbnail;
	private int categoryBlog_id;
	private String status;
	private List<String> imgUrls;
	public BlogDTO(int blog_id, String title, int author_id, LocalDate create_date, LocalTime create_time,
			LocalDate update_date, LocalTime update_time, String content, String thumbnail, int categoryBlog_id,
			String status, List<String> imgUrls) {
		super();
		this.blog_id = blog_id;
		this.title = title;
		this.author_id = author_id;
		this.create_date = create_date;
		this.create_time = create_time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.update_date = update_date;
		this.update_time = update_time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.content = content;
		this.thumbnail = thumbnail;
		this.categoryBlog_id = categoryBlog_id;
		this.status = status;
		this.imgUrls = imgUrls;
	}
	public BlogDTO(int blog_id, String title, int author_id, LocalDate create_date, LocalTime create_time,
			LocalDate update_date, LocalTime update_time, String content, List<String> imgUrls) {
		super();
		this.blog_id = blog_id;
		this.title = title;
		this.author_id = author_id;
		this.create_date = create_date;
		this.create_time = create_time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.update_date = update_date;
		this.update_time = update_time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.content = content;
		this.imgUrls = imgUrls;
	}
	
	
	
}
