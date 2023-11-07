package com.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "blog_img")
public class Blog_img {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int img_id;

	@Column
	private String imgUrl;
	
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog  blog;


    
    
}
