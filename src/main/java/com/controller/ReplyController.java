package com.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ReplyDTO;
import com.model.Feedback;
import com.model.Reply;
import com.repository.AccountRepository;
import com.repository.FeedbackRepository;
import com.repository.ReplyRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/")
public class ReplyController {
	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private AccountRepository accountRepository;

	// get reply list
	@GetMapping("/byFeedbackId/{feedbackId}")
	public List<ReplyDTO> getRepliesByFeedbackId(@PathVariable int feedbackId) {
		List<ReplyDTO> replyDTOs = new ArrayList<>();
		Feedback feedback = feedbackRepository.findById(feedbackId).orElse(null);
		List<Reply> replies = feedback.getReplyList();
		for (Reply reply : replies) {
			ReplyDTO replydto = new ReplyDTO();
			replydto.setReply_id(reply.getReply_id());
			replydto.setFeedback_id(feedback.getFeedback_id());
			LocalDate created_date = LocalDate.now();
			LocalTime create_time = LocalTime.now();
			
			reply.setCreated_date(created_date);
			reply.setCreated_at_time(create_time);
			replydto.setCreated_date(reply.getCreated_date());
			// Thiết lập user_id dựa trên cách xác định người dùng trong ứng dụng của bạn
			if (reply.getCreated_at_time() != null) {

				replydto.setCreated_at_time(reply.getCreated_at_time().format(DateTimeFormatter.ofPattern("HH:mm")));
			}
			replydto.setUser_id(feedback.getUser().getId());
			replydto.setUser_name(reply.getAccount().getName());
			replydto.setReply_feedback(reply.getReply_feedback());
			replyDTOs.add(replydto);
		}

		return replyDTOs;
	}

	// add reply
	@PostMapping("/feedback/reply/add")
	public ResponseEntity<ReplyDTO> addReply(@RequestBody ReplyDTO ReplyDTO) {
		// Tạo một đối tượng Reply và thiết lập thông tin từ DTO
		Reply reply = new Reply();
		reply.setReply_id(ReplyDTO.getReply_id());
		reply.setReply_feedback(ReplyDTO.getReply_feedback());
		LocalDate created_date = LocalDate.now();
		LocalTime create_time = LocalTime.now();
		
		reply.setCreated_date(created_date);
		reply.setCreated_at_time(create_time);
		ReplyDTO.setCreated_date(reply.getCreated_date());
		// Thiết lập user_id dựa trên cách xác định người dùng trong ứng dụng của bạn
		if (reply.getCreated_at_time() != null) {

			ReplyDTO.setCreated_at_time(reply.getCreated_at_time().format(DateTimeFormatter.ofPattern("HH:mm")));
		}
		reply.setAccount(accountRepository.findById(ReplyDTO.getUser_id()).orElse(null));
		ReplyDTO.setUser_name(reply.getAccount().getName());
		reply.setFeedback(feedbackRepository.findById(ReplyDTO.getFeedback_id()).orElse(null));

		replyRepository.save(reply);

		return new ResponseEntity<>(ReplyDTO, HttpStatus.CREATED);
	}
	
	

}
