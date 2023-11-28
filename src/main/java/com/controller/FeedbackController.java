package com.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.FeedbackDTO;
import com.model.Account;
import com.model.Feedback;
import com.model.Product;
import com.repository.AccountRepository;
import com.repository.FeedbackRepository;
import com.repository.ProductRepository;
import com.service.ProductService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/product/feedback/")
public class FeedbackController {
	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;

	@GetMapping("/{productId}")
	public List<FeedbackDTO> getProductFeedbacks(@PathVariable Integer productId) {
		List<FeedbackDTO> feedbackDTOs = new ArrayList<>();

		Product product = productService.getProductById(productId);
		if (product != null) {
			List<Feedback> feedbacks = product.getFeedbackList();
			feedbacks.sort(Comparator.comparing(Feedback::getFeedback_id)
					.thenComparing(Feedback::getCreated_at_time, Comparator.nullsLast(Comparator.naturalOrder()))
					.reversed());

			for (Feedback feedback : feedbacks) {
				FeedbackDTO feedbackDTO = new FeedbackDTO();
				feedbackDTO.setFeedback_id(feedback.getFeedback_id());
				feedbackDTO.setRating(feedback.getRating());
				feedbackDTO.setComment(feedback.getComment());
				if (feedback.getCreated_at_time() != null) {

					feedbackDTO.setCreated_at_time(
							feedback.getCreated_at_time().format(DateTimeFormatter.ofPattern("HH:mm")));
				}
				feedbackDTO.setCreated_date(feedback.getCreated_date());
				feedbackDTO.setProduct_id(product.getProduct_id());
				feedbackDTO.setP_name(product.getP_name());
				feedbackDTO.setUser_id(feedback.getUser().getId());
				feedbackDTO.setUser_name(feedback.getUser().getUsername());
				feedbackDTOs.add(feedbackDTO);
			}
		}

		return feedbackDTOs;
	}

//	"/pharmacy-online/product/feedback/"
	// delete feedback
	@DeleteMapping("delete/{feedbackId}/{user_id}")
	public ResponseEntity<String> deleteFeedback(@PathVariable int feedbackId, @PathVariable long user_id) {
		Feedback feedback = feedbackRepository.findById(feedbackId).orElse(null);
		Account account = accountRepository.findById(user_id).orElse(null);
		if (account == null) {
			return new ResponseEntity<>("account not found", HttpStatus.NOT_FOUND);
		}
		if (feedback == null) {
			return new ResponseEntity<>("Feedback not found", HttpStatus.NOT_FOUND);
		}
		feedbackRepository.delete(feedback);
		return new ResponseEntity<>("Feedback deleted successfully", HttpStatus.OK);
	}

	// add feedback
	@PostMapping("add/{product_id}/{user_id}")
	public ResponseEntity<?> addFeedback(@RequestBody FeedbackDTO feedbackDTO, @PathVariable int product_id,
			@PathVariable long user_id) {
		Account account = accountRepository.findById(user_id).orElse(null);
		if (account == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}
		Product product = productRepository.findById(product_id).orElse(null);
		Feedback feedback = new Feedback();
		if (product != null) {
			feedback.setComment(feedbackDTO.getComment());
			feedback.setRating(feedbackDTO.getRating());
			LocalDate created_date = LocalDate.now();
			LocalTime create_time = LocalTime.now();
			feedback.setCreated_date(created_date);
			feedback.setCreated_at_time(create_time);
			feedbackDTO.setCreated_date(feedback.getCreated_date());

			if (feedback.getCreated_at_time() != null) {

				feedbackDTO
						.setCreated_at_time(feedback.getCreated_at_time().format(DateTimeFormatter.ofPattern("HH:mm")));
			}

			feedback.setUser(accountRepository.findById(user_id).orElse(account));
			feedback.setProduct(productRepository.findById(product_id).orElse(product));
			feedbackDTO.setUser_id(feedback.getUser().getId());
			feedbackDTO.setProduct_id(feedback.getProduct().getProduct_id());
			feedbackDTO.setP_name(feedback.getProduct().getP_name());
			feedbackDTO.setUser_name(feedback.getUser().getUsername());
			feedbackRepository.save(feedback);
		}

		return new ResponseEntity<>(feedbackDTO, HttpStatus.OK);
	}

	// calculate average rating
	@GetMapping("/averageRating/{productId}")
	public double getAverageRating(@PathVariable Integer productId) {
		double average = 0;
		Product product = productService.getProductById(productId);
		if (product != null) {
			List<Feedback> feedbacks = product.getFeedbackList();
			int total = feedbacks.size();
			if (total > 0) {
				double totalRating = 0;

				for (Feedback feedback : feedbacks) {
					totalRating += feedback.getRating();
				}

				average = ((double) Math.round((totalRating / total) * 10) / 10);
			}
		}

		return average;
	}
	@GetMapping("/{productId}/countFeedback")
	public int countFeedbacksByProductId(@PathVariable int productId) {
        return feedbackRepository.countFeedbacksByProductId(productId);
    }

	//
	@GetMapping("/{productId}/{rating}")
	public int getTotalFeedbackByRating(@PathVariable int productId, @PathVariable int rating) {
		return feedbackRepository.countByRatingAndProductId(rating, productId);
	}

}
