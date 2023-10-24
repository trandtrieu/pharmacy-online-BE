package com.controller;

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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/prescriptions")
public class PrescriptionController {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private PrescriptionService prescriptionService;

	@GetMapping("/byaccount")
	public List<PrescriptionDTO> getPrescriptionsByAccountId(@RequestParam("account_id") Long accountId) {
		List<Prescription> prescriptions = prescriptionRepository.findPresciprionByAccountId(accountId);
		List<PrescriptionDTO> prescriptionDTOs = prescriptions.stream().map(prescription -> {
			PrescriptionDTO prescriptionDTO = new PrescriptionDTO(prescription.getId(), prescription.getNote(),
					prescription.getImageUrls(), prescription.getStatus(), prescription.getAccount().getName(),
					prescription.getAccount().getId(), prescription.getPhone(), prescription.getEmail());
			prescriptionDTO.setCreatedDate(prescription.getCreatedDate());
			prescriptionDTO
					.setCreatedTime(prescription.getCreatedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
			prescriptionDTO.setUpdatedDate(prescription.getUpdatedDate());
			if (prescription.getUpdatedTime() != null) {
				prescriptionDTO
						.setUpdatedTime(prescription.getUpdatedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
			}
			return prescriptionDTO;
		}).collect(Collectors.toList());
		return prescriptionDTOs;
	}

	@PostMapping("/create")
	public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Prescription prescription,
			@RequestParam("account_id") Long accountId) {
		LocalDate createdDate = LocalDate.now();
		LocalTime createdTime = LocalTime.now();
		prescription.setCreatedDate(createdDate);
		prescription.setCreatedTime(createdTime);

		Prescription createdPrescription = prescriptionService.createPrescription(prescription, accountId);
		PrescriptionDTO prescriptionDTO = new PrescriptionDTO(createdPrescription.getId(),
				createdPrescription.getNote(), createdPrescription.getImageUrls(), createdPrescription.getStatus(),
				createdPrescription.getName(), createdPrescription.getPhone(), createdPrescription.getEmail(),
				createdPrescription.getAccount().getId(), createdDate, createdTime);
		return new ResponseEntity<>(prescriptionDTO, HttpStatus.CREATED);
	}

	@PutMapping("/update/{prescriptionId}")
	public ResponseEntity<PrescriptionDTO> updatePrescription(@RequestBody Prescription prescription,
			@PathVariable Long prescriptionId) {

		LocalDate updatedDate = LocalDate.now();
		LocalTime updatedTime = LocalTime.now();
		prescription.setUpdatedDate(updatedDate);
		prescription.setUpdatedTime(updatedTime);
		Prescription updatedPrescription = prescriptionService.updatePrescription(prescription, prescriptionId);

		// Create a PrescriptionDTO from updatedPrescription
		PrescriptionDTO prescriptionDTO = new PrescriptionDTO(updatedPrescription.getId(),
				updatedPrescription.getNote(), updatedPrescription.getImageUrls(), updatedPrescription.getStatus(),
				updatedPrescription.getName(), updatedPrescription.getPhone(), updatedPrescription.getEmail(),
				updatedPrescription.getAccount().getId(), updatedPrescription.getCreatedDate(),
				updatedPrescription.getCreatedTime(), updatedDate, updatedTime);

		return new ResponseEntity<>(prescriptionDTO, HttpStatus.OK);
	}

	@GetMapping("/view/{prescriptionId}")
	public ResponseEntity<PrescriptionDTO> viewPrescriptionDetails(@PathVariable Long prescriptionId) {
		Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);

		if (prescription == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		PrescriptionDTO prescriptionDTO = new PrescriptionDTO(prescription.getId(), prescription.getNote(),
				prescription.getImageUrls(), prescription.getStatus(), prescription.getName(), prescription.getPhone(),
				prescription.getEmail(), prescription.getAccount().getId(), prescription.getCreatedDate(),
				prescription.getCreatedTime(), prescription.getUpdatedDate(), prescription.getUpdatedTime());

		return new ResponseEntity<>(prescriptionDTO, HttpStatus.OK);
	}
	@DeleteMapping("/delete/{prescriptionId}")
	public ResponseEntity<String> deletePrescription(@PathVariable Long prescriptionId) {
		boolean deleted = prescriptionService.deletePrescription(prescriptionId);
		if (deleted) {
			return new ResponseEntity<>("Prescription deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Prescription not found or could not be deleted", HttpStatus.NOT_FOUND);
		}
	}
	
	
}
