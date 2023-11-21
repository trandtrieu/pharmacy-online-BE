package com.controller.admin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.PrescriptionDTO;
import com.model.Prescription;
import com.repository.PrescriptionRepository;
import com.service.EmailService;
import com.service.PrescriptionService;

@CrossOrigin(origins = "http://localhost:3006")
@RestController
@RequestMapping("/pharmacy-online/admin/prescriptions")
public class PrescriptionAdminController {
	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private PrescriptionService prescriptionService;
	@Autowired
	private EmailService emailService;

	@GetMapping("/list")
	public List<PrescriptionDTO> getAllPrescriptions(@RequestParam(required = false) Integer status) {
		return prescriptionRepository.findAll().stream().filter(p -> status == null || p.getStatus() == status)
				.map(this::createPrescriptionDTO).sorted(Comparator.comparing(PrescriptionDTO::getCreatedDate)
						.thenComparing(PrescriptionDTO::getCreatedTime))
				.collect(Collectors.toList());
	}

	@GetMapping("/view/{prescriptionId}")
	public ResponseEntity<PrescriptionDTO> viewPrescriptionDetails(@PathVariable Long prescriptionId) {
		Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
		return (prescription != null) ? new ResponseEntity<>(createPrescriptionDTO(prescription), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/create")
	public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Prescription prescription,
			@RequestParam("account_id") Long accountId) {
		prescription.setCreatedDate(LocalDate.now());
		prescription.setCreatedTime(LocalTime.now());
		Prescription createdPrescription = prescriptionService.createPrescription(prescription, accountId);
		return ResponseEntity.status(HttpStatus.CREATED).body(createPrescriptionDTO(createdPrescription));
	}

	@PutMapping("/update-status/{prescriptionId}")
	public ResponseEntity<PrescriptionDTO> updatePrescriptionStatus(@PathVariable Long prescriptionId,
			@RequestParam("status") int newStatus) {
		Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
		if (prescription == null) {
			return ResponseEntity.notFound().build();
		}
		prescription.setStatus(newStatus);
		Prescription updatedPrescription = prescriptionService.updatePrescription(prescription, prescriptionId);
		return ResponseEntity.ok(createPrescriptionDTO(updatedPrescription));
	}

	@DeleteMapping("/delete/{prescriptionId}")
	public ResponseEntity<String> deletePrescription(@PathVariable Long prescriptionId) {
		if (prescriptionService.deletePrescription(prescriptionId)) {
			return ResponseEntity.ok("Prescription deleted successfully");
		}
		return ResponseEntity.notFound().build();
	}

	private PrescriptionDTO createPrescriptionDTO(Prescription prescription) {
		PrescriptionDTO prescriptionDTO = new PrescriptionDTO(prescription.getId(), prescription.getNote(),
				prescription.getImageUrls(), prescription.getStatus(), prescription.getName(),
				prescription.getAccount().getId(), prescription.getPhone(), prescription.getEmail());
		prescriptionDTO.setCreatedDate(prescription.getCreatedDate());
		prescriptionDTO.setCreatedTime(prescription.getCreatedTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		prescriptionDTO.setUpdatedDate(prescription.getUpdatedDate());
		prescriptionDTO.setUpdatedTime(prescription.getUpdatedTime() != null
				? prescription.getUpdatedTime().format(DateTimeFormatter.ofPattern("HH:mm"))
				: null);
		return prescriptionDTO;
	}

//	@PostMapping("/send-email")
//	public ResponseEntity<String> sendConfirmationEmail(@RequestParam Long prescriptionId,
//			@RequestParam String customSubject, @RequestParam String customMessage) {
//		try {
//			Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
//			if (prescription != null) {
//				sendEmail(prescription, customSubject, customMessage);
//				return ResponseEntity.ok("Email sent successfully!");
//			} else {
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prescription not found");
//			}
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
//		}
//	}
//
//	private void sendEmail(Prescription prescription, String customSubject, String customMessage) {
//		String subject = customSubject.isEmpty() ? "Your Prescription Have Been Proccessed" : customSubject;
//		String recipient = prescription.getEmail();
//		String message = customMessage.isEmpty()
//				? "Your prescription has been created successfully. Prescription ID: " + prescription.getId()
//				: customMessage;
//
//		emailService.sendEmail(recipient, subject, message);
//	}
	
	
	@PostMapping("/send-email")
	public ResponseEntity<String> sendConfirmationEmail(@RequestParam Long prescriptionId,
			@RequestParam String customSubject, @RequestParam String customMessage, @RequestParam String customContent) {
		try {
			Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
			if (prescription != null) {
				sendEmail(prescription, customSubject, customMessage, customContent);
				return ResponseEntity.ok("Email sent successfully!");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prescription not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
		}
	}

	private void sendEmail(Prescription prescription, String customSubject, String customMessage, String customContent) {
		String subject = customSubject.isEmpty() ? "Your Prescription Have Been Proccessed" : customSubject;
		String recipient = prescription.getEmail();
		String message = customMessage.isEmpty()
				? "Your prescription has been created successfully. Prescription ID: " + prescription.getId()
				: customMessage;
		String content = customContent;

		emailService.sendEmail2(recipient, subject, message, content);
	}

}