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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
					prescription.getAccount().getId());

			prescriptionDTO.setCreatedDate(prescription.getCreatedDate());
			prescriptionDTO
					.setCreatedTime(prescription.getCreatedTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

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
				createdPrescription.getName(), createdPrescription.getPhone(), createdPrescription.getEmail(), createdPrescription.getAccount().getId(), createdDate,
				createdTime);
		return new ResponseEntity<>(prescriptionDTO, HttpStatus.CREATED);
	}
}
