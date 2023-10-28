package com.controller.admin;

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
import com.service.PrescriptionService;

@CrossOrigin(origins = "http://localhost:3006")
@RestController
@RequestMapping("/pharmacy-online/admin/prescriptions")
public class PrescriptionAdminController {
	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private PrescriptionService prescriptionService;

	@GetMapping("/list")
	public List<PrescriptionDTO> getAllPrescriptions() {
		return prescriptionRepository.findAll().stream()
				.map(prescription -> createPrescriptionDTO(prescription)).collect(Collectors.toList());
	}	
	@PostMapping("/create")
	public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Prescription prescription,
			@RequestParam("account_id") Long accountId) {
		LocalDate createdDate = LocalDate.now();
		LocalTime createdTime = LocalTime.now();
		prescription.setCreatedDate(createdDate);
		prescription.setCreatedTime(createdTime);
		Prescription createdPrescription = prescriptionService.createPrescription(prescription, accountId);
		return new ResponseEntity<>(createPrescriptionDTO(createdPrescription), HttpStatus.CREATED);
	}
	
	@PutMapping("/update-status/{prescriptionId}")
	public ResponseEntity<PrescriptionDTO> updatePrescriptionStatus(@PathVariable Long prescriptionId,
			@RequestParam("status") int newStatus) {
		Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);

		if (prescription == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		prescription.setStatus(newStatus);
		Prescription updatedPrescription = prescriptionService.updatePrescription(prescription, prescriptionId);

		return new ResponseEntity<>(createPrescriptionDTO(updatedPrescription), HttpStatus.OK);
	}
	@PutMapping("/update/{prescriptionId}")
	public ResponseEntity<PrescriptionDTO> updatePrescription(@RequestBody Prescription prescription,
			@PathVariable Long prescriptionId) {
		LocalDate updatedDate = LocalDate.now();
		LocalTime updatedTime = LocalTime.now();
		prescription.setUpdatedDate(updatedDate);
		prescription.setUpdatedTime(updatedTime);
		Prescription updatedPrescription = prescriptionService.updatePrescription(prescription, prescriptionId);
		return new ResponseEntity<>(createPrescriptionDTO(updatedPrescription), HttpStatus.OK);
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
}
