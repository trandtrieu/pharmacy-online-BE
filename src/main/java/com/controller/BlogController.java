package com.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.BlogDTO;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/blog/")
public class BlogController {

	private final BlogDetailController blogDetailController;



	public BlogController(BlogDetailController blogDetailController) {
		this.blogDetailController = blogDetailController;
	}


	@PostMapping("/add")
	public ResponseEntity<String> addBlog(@RequestBody BlogDTO blogDTO) {
		blogDetailController.addBlog(blogDTO);
		return ResponseEntity.ok("Blog đã thêm thành công");
	}
	
	//update blog rest api
	@PutMapping("/update/{blog_id}")
	public ResponseEntity<?> updateBlog(@PathVariable Integer blog_id, @RequestBody BlogDTO blogDTO){
		blogDetailController.updateBlog(blog_id, blogDTO);
		return ResponseEntity.ok("Update thành công");
	}
}
