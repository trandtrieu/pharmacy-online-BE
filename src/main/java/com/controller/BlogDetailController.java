package com.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.BlogDTO;
import com.exception.ResourceNotFoundException;
import com.model.Blog;
import com.model.Blog_img;
import com.repository.BlogImageRepository;
import com.repository.BlogRepository;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/blogs/")
public class BlogDetailController {
	@Autowired
	private BlogImageRepository blogImageRepository;

	@Autowired
	private BlogRepository blogRepository;

	// list blog
	@GetMapping("/list")
	public List<BlogDTO> getAllBlogWithImage() {
		List<BlogDTO> blogDTOs = new ArrayList<>();

		List<Blog> blogs = blogRepository.findAll();
		for (Blog blog : blogs) {
			List<Blog_img> blogImages = blogImageRepository.findByBlog(blog);

			BlogDTO blogDTO = new BlogDTO();
			blogDTO.setBlog_id(blog.getBlog_id());
			blogDTO.setTitle(blog.getTitle());
			blogDTO.setAuthor_id(blog.getAuthor_id());
			blogDTO.setCreate_date(blog.getCreate_date());

			if (blog.getCreate_time() != null) {
				blogDTO.setCreate_time(blog.getCreate_time().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
			}
//		blogDTO.setUpdate_date(blog.getUpdate_date());
//		blogDTO.setUpdate_time(blog.getUpdate_time().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
			blogDTO.setContent(blog.getContent());
			blogDTO.setThumbnail(blog.getThumbnail());
			blogDTO.setCategoryBlog_id(blog.getCategoryBlog_id());
			blogDTO.setStatus(blog.getStatus());

			List<String> imgUrls = new ArrayList<>();
			for (Blog_img blogImage : blogImages) {
				imgUrls.add(blogImage.getImgUrl());
			}
			blogDTO.setImgUrls(imgUrls);
			blogDTOs.add(blogDTO);
		}

		return blogDTOs;
	}

	@GetMapping("/view/{blog_id}")
	public ResponseEntity<BlogDTO> getBlogDetailById(@PathVariable Integer blog_id) {
		Blog blog = blogRepository.findById(blog_id).orElse(null);

		if (blog == null) {
			return ResponseEntity.notFound().build();
		}
		BlogDTO blogDTO = new BlogDTO();
		blogDTO.setBlog_id(blog.getBlog_id());
		blogDTO.setTitle(blog.getTitle());
		blogDTO.setAuthor_id(blog.getAuthor_id());
		blogDTO.setCreate_date(blog.getCreate_date());

		
		if (blog.getCreate_time() != null) {
			blogDTO.setCreate_time(blog.getCreate_time().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		}
//		blogDTO.setCreate_time(blog.getCreate_time().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//		blogDTO.setUpdate_date(blog.getUpdate_date());
//		blogDTO.setUpdate_time(blog.getUpdate_time().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		blogDTO.setContent(blog.getContent());
		blogDTO.setThumbnail(blog.getThumbnail());
		blogDTO.setCategoryBlog_id(blog.getCategoryBlog_id());
		blogDTO.setStatus(blog.getStatus());

		List<Blog_img> blogImgs = blogImageRepository.findByBlog(blog);
		List<String> imgUrls = new ArrayList<>();
		for (Blog_img blogImg : blogImgs) {
			imgUrls.add(blogImg.getImgUrl());
		}
		blogDTO.setImgUrls(imgUrls);
		return ResponseEntity.ok(blogDTO);
	}

	// delete blog rest api
	@DeleteMapping("/delete/{blog_id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Integer blog_id) {
		Blog blog = blogRepository.findById(blog_id)
				.orElseThrow(() -> new ResourceNotFoundException("Blog not exit with id" + blog_id));

		blogRepository.delete(blog);
		Map<String, Boolean> response = new HashMap<>();

		response.put("delete", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	// update blog rest api
	@Transactional
	public void updateBlog(Integer blog_id, BlogDTO blogDTO) {
		Optional<Blog> blogOptional = blogRepository.findById(blog_id);

		if (blogOptional.isPresent()) {
			Blog blog = blogOptional.get();
			LocalDate updateDate = LocalDate.now();
			LocalTime updateTime = LocalTime.now();

			
			// cập nhât thông tin blog
//			blog.setBlog_id(blogDTO.getBlog_id());
			blog.setTitle(blogDTO.getTitle());
			blog.setAuthor_id(blogDTO.getAuthor_id());
			blog.setUpdate_date(blogDTO.getUpdate_date());
			blog.setUpdate_date(updateDate);
			blog.setUpdate_time(updateTime);
			blog.setContent(blogDTO.getContent());

			blogRepository.save(blog);

			List<String> imgUrls = blogDTO.getImgUrls();
			if (imgUrls != null && !imgUrls.isEmpty()) {
				List<Blog_img> blogImgs = new ArrayList<>();

				for (String imgUrl : imgUrls) {
					Blog_img blog_img = new Blog_img();
					blog_img.setBlog(blog);
					blog_img.setImgUrl(imgUrl);
					blogImgs.add(blog_img);
				}
				blogImageRepository.saveAll(blogImgs);
			}
		}
	}

	// add blog rest api
	@Transactional
	public void addBlog(BlogDTO blogDTO) {

		Blog blog = new Blog();
		LocalDate createdDate = LocalDate.now();
		LocalTime createdTime = LocalTime.now();

		blog.setBlog_id(blogDTO.getBlog_id());
		blog.setTitle(blogDTO.getTitle());
		blog.setAuthor_id(blogDTO.getAuthor_id());
		blog.setCreate_date(blogDTO.getCreate_date());
		blog.setCreate_date(createdDate);
		blog.setCreate_time(createdTime);
		blog.setContent(blogDTO.getContent());

		blogRepository.save(blog);

		List<String> imgUrls = blogDTO.getImgUrls();
		if (imgUrls != null && !imgUrls.isEmpty()) {
			List<Blog_img> blogImgs = new ArrayList<>();
			for (String imgUrl : imgUrls) {
				Blog_img blog_img = new Blog_img();
				blog_img.setBlog(blog);
				blog_img.setImgUrl(imgUrl);
				blogImgs.add(blog_img);
			}
			blogImageRepository.saveAll(blogImgs);
		}
	}

}
