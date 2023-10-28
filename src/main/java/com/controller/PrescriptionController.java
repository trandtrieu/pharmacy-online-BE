package com.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dto.PrescriptionDTO;
import com.model.Prescription;
import com.repository.PrescriptionRepository;
import com.service.EmailService;
import com.service.PrescriptionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/prescriptions")
public class PrescriptionController {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private PrescriptionService prescriptionService;
	@Autowired
	private EmailService emailService;

	@GetMapping("/byaccount")
	public List<PrescriptionDTO> getPrescriptionsByAccountId(@RequestParam("account_id") Long accountId) {
		return prescriptionRepository.findPresciprionByAccountId(accountId).stream()
				.map(prescription -> createPrescriptionDTO(prescription)).collect(Collectors.toList());
	}

	@PostMapping("/create")
	public ResponseEntity<String> createPrescription(@ModelAttribute Prescription prescription,
			@RequestParam("account_id") Long accountId, @RequestParam("imageFile") MultipartFile imageFile) {
		LocalDate createdDate = LocalDate.now();
		LocalTime createdTime = LocalTime.now();
		prescription.setCreatedDate(createdDate);
		prescription.setCreatedTime(createdTime);

		if (imageFile != null) {
			ResponseEntity<String> uploadResponse = uploadImage(imageFile);
			if (uploadResponse.getStatusCode() != HttpStatus.OK) {
				return uploadResponse;
			}
			prescription.setImageUrls(imageFile.getOriginalFilename());
		}

		Prescription createdPrescription = prescriptionService.createPrescription(prescription, accountId);
//		sendConfirmationEmail(createdPrescription);

		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}

	@PutMapping("/update/{prescriptionId}")
	public ResponseEntity<String> updatePrescription(@PathVariable Long prescriptionId,
			@ModelAttribute Prescription updatedPrescription, @RequestParam("imageFile") MultipartFile imageFile) {
		Prescription existingPrescription = prescriptionService.getPrescriptionById(prescriptionId);

		if (existingPrescription == null) {
			return new ResponseEntity<>("Prescription not found", HttpStatus.NOT_FOUND);
		}

		LocalDate updatedDate = LocalDate.now();
		LocalTime updatedTime = LocalTime.now();
		updatedPrescription.setUpdatedDate(updatedDate);
		updatedPrescription.setUpdatedTime(updatedTime);

		if (imageFile != null) {
			ResponseEntity<String> uploadResponse = uploadImage(imageFile);
			if (uploadResponse.getStatusCode() != HttpStatus.OK) {
				return uploadResponse; 
			}
			updatedPrescription.setImageUrls(imageFile.getOriginalFilename());
		} else {
			updatedPrescription.setImageUrls(existingPrescription.getImageUrls());
		}

		prescriptionService.updatePrescription(updatedPrescription, prescriptionId);
		return new ResponseEntity<String>("Prescription updated successfully", HttpStatus.OK);
	}

	@GetMapping("/view/{prescriptionId}")
	public ResponseEntity<PrescriptionDTO> viewPrescriptionDetails(@PathVariable Long prescriptionId) {
		Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
		return (prescription != null) ? new ResponseEntity<>(createPrescriptionDTO(prescription), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{prescriptionId}")
	public ResponseEntity<String> deletePrescription(@PathVariable Long prescriptionId) {
		if (prescriptionService.deletePrescription(prescriptionId)) {
			return new ResponseEntity<>("Prescription deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Prescription not found or could not be deleted", HttpStatus.NOT_FOUND);
	}

	private PrescriptionDTO createPrescriptionDTO(Prescription prescription) {
		PrescriptionDTO prescriptionDTO = new PrescriptionDTO(prescription.getId(), prescription.getNote(),
				prescription.getImageUrls(), prescription.getStatus(), prescription.getAccount().getName(),
				prescription.getAccount().getId(), prescription.getPhone(), prescription.getEmail());
		prescriptionDTO.setCreatedDate(prescription.getCreatedDate());
		prescriptionDTO.setCreatedTime(prescription.getCreatedTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		prescriptionDTO.setUpdatedDate(prescription.getUpdatedDate());
		if (prescription.getUpdatedTime() != null) {
			prescriptionDTO.setUpdatedTime(prescription.getUpdatedTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		}
		return prescriptionDTO;
	}

	private void sendConfirmationEmail(Prescription prescription) {
		String subject = "Prescription Created";
		String recipient = prescription.getEmail();
		String message = "Your prescription has been created successfully. Prescription ID: " + prescription.getId();

		emailService.sendEmail(recipient, subject, message);
	}

	@PostMapping("/uploadImage")
	public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
		if (imageFile != null) {
			String uploadFolderPath = "D:/Documents/OJT/mock-project/pharmacy-online-fe/public/assets/images";
			String fileName = imageFile.getOriginalFilename();

			try {
				Path imagePath = Paths.get(uploadFolderPath, fileName);
				Files.write(imagePath, imageFile.getBytes());
				return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
			} catch (IOException e) {
				return new ResponseEntity<>("Failed to upload the image", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>("No image file provided", HttpStatus.BAD_REQUEST);
		}
	}

}
